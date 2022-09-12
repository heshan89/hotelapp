package com.hotel.dao.enums;

public enum ItemEnum {
	PILLOW_CASE("Pillow Case",1),
	S_SHEET("S-Sheet",2),
	D_SHEET("D-Sheet",3),
    CLEANER("Cleaner",4),
    YUKATA("Yukata",5),
    SLIPPER("Slipper",6),
    BATH_ROBE("Bath Robe",7),
    BATH_TOWEL("Bath Towel",8);

	private ItemEnum (String code, Integer value) {
		this.code = code;
		this.value = value;
	}
	
    private String code;
    private Integer value;

    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }

    public static ItemEnum getEnum(String code) {
        switch (code) {
            case "Pillow Case":
                return PILLOW_CASE;
            case "S-Sheet":
                return S_SHEET;
            case "D-Sheet":
                return D_SHEET;
            case "Cleaner":
                return CLEANER;
            case "Yukata":
                return YUKATA;
            case "Slipper":
                return SLIPPER;
            case "Bath Robe":
                return BATH_ROBE;
            case "Bath Towel":
                return BATH_TOWEL;
            default:
                return null;
        }
    }
}
