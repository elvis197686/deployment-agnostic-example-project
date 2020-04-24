package com.scw.devops.contract.store.common.data;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFilters {

	static boolean filterByBooleanProperty(Boolean actualValue, boolean defaultActualValue,
			Optional<Boolean> wantedValue) {
		if (!wantedValue.isPresent()) {
			return true;
		}
		boolean actualValueForComparison = defaultActualValue;
		if (actualValue != null) {
			actualValueForComparison = actualValue;
		}
		return wantedValue.get().equals(actualValueForComparison);
	}

	static boolean filterByName(String actualName, Optional<String> wantedName) {
		if (!wantedName.isPresent()) {
			return true;
		}
		Pattern pattern = Pattern.compile(wantedName.get());
		Matcher matcher = pattern.matcher(actualName);
		return matcher.find();
	}

	static boolean filterByExactString(Optional<String> actualString, boolean unsetOnly,
			Optional<String> wantedString) {
		if (unsetOnly) {
			return actualString.isEmpty();
		}
		if (!wantedString.isPresent()) {
			return true;
		}
		if (!actualString.isPresent()) {
			return false;
		}
		return actualString.get().equals(wantedString.get());
	}

	static boolean filterByErrorState(boolean hasError, Optional<Boolean> wantedValidityState) {
		if (!wantedValidityState.isPresent()) {
			return true;
		}
		return wantedValidityState.get() != hasError;
	}

}
