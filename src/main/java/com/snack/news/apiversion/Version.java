package com.snack.news.apiversion;

public enum Version {
	V1("v1"), DEFAULT("default");
	String name;

	Version(String name) {
		this.name = name;
	}

	public boolean is(Version version) {
		if (version == null) {
			return false;
		}

		return this.equals(version);
	}
}
