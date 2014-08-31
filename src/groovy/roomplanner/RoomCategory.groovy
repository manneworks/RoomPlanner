package roomplanner

import roomplanner.api.RoomCategory as RoomCategoryDto

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("RoomCategory")
class RoomCategory {
	Long id

	/**
		Creates Domain object from DTO
	*/
	static RoomCategory fromDto(RoomCategoryDto dto) {
		new RoomCategory( 
			id: dto.id
		) 
	}

	/**
		Renders object to DTO
	*/
	RoomCategoryDto toDto() {
		new RoomCategoryDto(
			id: id
		)
	}
}
