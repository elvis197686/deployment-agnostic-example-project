package com.scw.devops.contract.store.common.data;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MappableDefinitionReference {

	private final String name;
	private final ProjectVersion version;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		result = prime * result + ( ( version == null ) ? 0 : SharedProjectVersionProcessor.getSingleVersionString( version ).hashCode() );
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		MappableDefinitionReference other = (MappableDefinitionReference) obj;
		if ( name == null ) {
			if ( other.name != null )
				return false;
		}
		else if ( !name.equals( other.name ) )
			return false;
		if ( version == null ) {
			if ( other.version != null )
				return false;
		}
		else if ( !SharedProjectVersionProcessor.getSingleVersionString( version )
			.equals( SharedProjectVersionProcessor.getSingleVersionString( other.version ) ) )
			return false;
		return true;
	}

}
