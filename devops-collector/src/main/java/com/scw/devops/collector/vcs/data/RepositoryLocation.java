package com.scw.devops.collector.vcs.data;

public class RepositoryLocation {

	private final String group;
	private final String project;

	public RepositoryLocation(final String group, final String project) {
		this.group = group;
		this.project = project;
	}

	public String getGroupName() {
		return group;
	}

	public String getProjectName() {
		return project;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepositoryLocation other = (RepositoryLocation) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

}
