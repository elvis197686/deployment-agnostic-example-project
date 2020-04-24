package com.scw.devops.store;

public class ProjectVersionTest {

	// TODO
	//	@Test
	//	public void shouldAcceptHigherVersions() {
	//		String higherThan010 = "^0.2.0";
	//		Assert.assertFalse(makeVersion("0.1.0").compliesTo(higherThan010));
	//		Assert.assertTrue(makeVersion("0.2.0").compliesTo(higherThan010));
	//		Assert.assertTrue(makePreview().compliesTo(higherThan010));
	//	}
	//
	//	@Test
	//	public void shouldMatchExactVersionAndPreview() {
	//		String version010 = "0.1.0";
	//		Assert.assertTrue(makeVersion("0.1.0").compliesTo(version010));
	//		Assert.assertFalse(makeVersion("0.2.0").compliesTo(version010));
	//		Assert.assertTrue(makePreview().compliesTo(version010));
	//	}
	//
	//	@Test
	//	public void shouldMatchPreviewVersionOnly() {
	//		String version = "0.3.0";
	//		Assert.assertFalse(makeVersion("0.1.0").compliesTo(version));
	//		Assert.assertFalse(makeVersion("0.2.0").compliesTo(version));
	//		Assert.assertTrue(makePreview().compliesTo(version));
	//	}
	//
	//	@Test
	//	public void shouldOrderByVersion() {
	//		Collection<ProjectVersion> allVersions = Arrays.asList(makeVersion("0.3.0"), makeVersion("0.1.0"),
	//				makeVersion("0.2.0"));
	//		List<String> sortedVersionStrings = allVersions.stream().sorted((a, b) -> a.compareTo(b))
	//				.map(v -> v.getSingleVersionString()).collect(Collectors.toList());
	//		Assert.assertThat(sortedVersionStrings, Matchers.contains("0.3.0", "0.2.0", "0.1.0"));
	//	}
	//
	//	@Test
	//	public void shouldOrderPreviewFirst() {
	//		Collection<ProjectVersion> allVersions = Arrays.asList(makeVersion("0.1.0"), makePreview(),
	//				makeVersion("0.2.0"));
	//		List<String> sortedVersionStrings = allVersions.stream().sorted((a, b) -> a.compareTo(b))
	//				.map(v -> v.getSingleVersionString()).collect(Collectors.toList());
	//		Assert.assertThat(sortedVersionStrings, Matchers.contains("preview", "0.2.0", "0.1.0"));
	//	}
	//
	//	@Test
	//	public void shouldOrderInvalidLast() {
	//		Collection<ProjectVersion> allVersions = Arrays.asList(makeVersion("badversion0.3.0"), makeVersion("0.1.0"),
	//				makeVersion("0.2.0"));
	//		List<String> sortedVersionStrings = allVersions.stream().sorted((a, b) -> a.compareTo(b))
	//				.map(v -> v.getSingleVersionString()).collect(Collectors.toList());
	//		Assert.assertThat(sortedVersionStrings, Matchers.contains("0.2.0", "0.1.0", "badversion0.3.0"));
	//	}
	//
	//	@Test
	//	public void shouldFindCorrectVersionInMap() {
	//		Map<ProjectVersion, String> versionMap = new HashMap<>();
	//		versionMap.put(makeVersion("0.1.0"), "0.1.0");
	//		versionMap.put(makePreview(), "preview");
	//		versionMap.put(makeVersion("0.2.0"), "0.2.0");
	//
	//		Assert.assertThat(versionMap.get(makeVersion("0.1.0")), is("0.1.0"));
	//	}
	//
	//	@Test
	//	public void shouldFindPreviewVersionInMap() {
	//		Map<ProjectVersion, String> versionMap = new HashMap<>();
	//		versionMap.put(makeVersion("0.1.0"), "0.1.0");
	//		versionMap.put(makePreview(), "preview");
	//		versionMap.put(makeVersion("0.2.0"), "0.2.0");
	//
	//		Assert.assertThat(versionMap.get(new ProjectVersion("ignored", true)), is("preview"));
	//	}
	//
	//	private ProjectVersion makeVersion(String version) {
	//		return new ProjectVersion(version, false);
	//	}
	//
	//	private ProjectVersion makePreview() {
	//		return new ProjectVersion("anystring", true);
	//	}
}
