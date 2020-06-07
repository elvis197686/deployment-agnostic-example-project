package com.scw.devops.contract.store.common.data;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFilters {

	static boolean filterByBooleanProperty( final Optional<Boolean> actualValue,
											final boolean defaultActualValue,
			final Optional<Boolean> wantedValue) {
		if (!wantedValue.isPresent()) {
			return true;
		}
		boolean actualValueForComparison = actualValue.orElse( defaultActualValue );
		return wantedValue.get().equals(actualValueForComparison);
	}

	static boolean filterByName(final String actualName, final Optional<String> wantedName) {
		if (!wantedName.isPresent()) {
			return true;
		}
		Pattern pattern = Pattern.compile(wantedName.get());
		Matcher matcher = pattern.matcher(actualName);
		return matcher.find();
	}

	static boolean filterByExactString(final Optional<String> actualString, final boolean unsetOnly,
			final Optional<String> wantedString) {
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

	static boolean filterByErrorState(final boolean hasError, final Optional<Boolean> wantedValidityState) {
		if (!wantedValidityState.isPresent()) {
			return true;
		}
		return wantedValidityState.get() != hasError;
	}

}
