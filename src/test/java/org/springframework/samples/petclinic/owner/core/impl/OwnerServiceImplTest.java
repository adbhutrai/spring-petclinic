/**
 * 
 */
package org.springframework.samples.petclinic.owner.core.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.core.OwnerService;
import org.springframework.samples.petclinic.owner.port.adaptor.OwnerUIAdptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

/**
 * Tests for {@link OwnerServiceImpl}.
 * 
 * @author adbhutrai
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OwnerServiceImpl.class)
public class OwnerServiceImplTest {
	private static final int TEST_OWNER_ID = 1;
	@Autowired
	private OwnerService ownerService;

	@MockBean
	private OwnerRepository owners;

	private Owner george;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(george);
	}

	/**
	 * Test method for
	 * {@link org.springframework.samples.petclinic.owner.core.impl.OwnerServiceImpl#createOwner(org.springframework.samples.petclinic.owner.Owner, org.springframework.validation.BindingResult, org.springframework.samples.petclinic.owner.port.OwnerPort)}.
	 */
	@Test
	public void testCreateOwner() throws Exception {
		BindingResult result = mock(BindingResult.class);
		OwnerUIAdptor uiPort = new OwnerUIAdptor(result, null);

		ownerService.createOwner(george, result, uiPort);

		assertThat(uiPort.getViewName()).isEqualTo("redirect:/owners/" + TEST_OWNER_ID);
	}

	/**
	 * Test method for when CreateOwner has error in binding.
	 * {@link org.springframework.samples.petclinic.owner.core.impl.OwnerServiceImpl#createOwner(org.springframework.samples.petclinic.owner.Owner, org.springframework.validation.BindingResult, org.springframework.samples.petclinic.owner.port.OwnerPort)}.
	 */
	@Test
	public void testCreateOwnerWithErrorForRedirection() throws Exception {
		Owner owner = mock(Owner.class);
		BindingResult result = mock(BindingResult.class);
		given(result.hasErrors()).willReturn(true);
		OwnerUIAdptor uiPort = new OwnerUIAdptor(result, null);

		ownerService.createOwner(owner, result, uiPort);

		assertThat(uiPort.getViewName()).isEqualTo("owners/createOrUpdateOwnerForm");
	}

	/**
	 * Test method for
	 * {@link org.springframework.samples.petclinic.owner.core.impl.OwnerServiceImpl#findOwners(org.springframework.samples.petclinic.owner.Owner, org.springframework.samples.petclinic.owner.port.OwnerPort)}.
	 */
	@Test
	public void testFindOwnersNoRecord() throws Exception {
		Map<String, Object> model = new HashMap<>();
		BindingResult bindingResult = mock(BindingResult.class);
		OwnerUIAdptor uiPort = new OwnerUIAdptor(bindingResult, model);

		given(this.owners.findByLastName("")).willReturn(Lists.emptyList());

		ownerService.findOwners(new Owner(), uiPort);

		assertThat(uiPort.getViewName()).isEqualTo("owners/findOwners");

	}

	/**
	 * Test method for
	 * {@link org.springframework.samples.petclinic.owner.core.impl.OwnerServiceImpl#findOwners(org.springframework.samples.petclinic.owner.Owner, org.springframework.samples.petclinic.owner.port.OwnerPort)}.
	 */
	@Test
	public void testFindOwnersSuccessForBlankLastName() throws Exception {
		Owner testOwner = new Owner();
		Map<String, Object> model = new HashMap<>();
		OwnerUIAdptor uiPort = new OwnerUIAdptor(null, model);

		given(this.owners.findByLastName("")).willReturn(Lists.newArrayList(george, testOwner));

		ownerService.findOwners(new Owner(), uiPort);

		assertThat(uiPort.getViewName()).isEqualTo("owners/ownersList");
		Map<String, Object> result = uiPort.getModel();
		assertThat(result).isNotEmpty();
		assertThat(result.get("selections"), is(Lists.newArrayList(george, testOwner)));
	}

	/**
	 * Test method for
	 * {@link org.springframework.samples.petclinic.owner.core.impl.OwnerServiceImpl#findOwners(org.springframework.samples.petclinic.owner.Owner, org.springframework.samples.petclinic.owner.port.OwnerPort)}.
	 */
	@Test
	public void testFindOwnersSuccessWithOneRecord() throws Exception {
		Map<String, Object> model = new HashMap<>();
		OwnerUIAdptor uiPort = new OwnerUIAdptor(null, model);

		given(this.owners.findByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));

		ownerService.findOwners(george, uiPort);

		assertThat(uiPort.getViewName()).isEqualTo("owners/ownersList");
		Map<String, Object> result = uiPort.getModel();
		assertThat(result).isNotEmpty();
		assertThat(result.get("selections"), is(Lists.newArrayList(george)));

	}

}
