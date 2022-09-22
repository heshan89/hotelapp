package com.hotel.dao.enums;

public enum ItemEnum {
	PILLOW_CASE("Pillow Case",1),
	S_SHEET("S-Sheet",2),
	D_SHEET("D-Sheet",3),
    CLEANER("Cleaner",4),
    YUKATHA("Yukatha",5),
    SLIPPERS("Slippers",6),
    BATH_ROBE("Bath Robe",7),
    BATH_TOWEL("Bath Towel",8),
	K_SHEET("K-Sheet",9),
	HAND_TOWEL("Hand Towel",10),
	BATH_MAT("Bath Mat",11),
	NIGHT_WEAR("Night Wear",12),
	DUSTER("Duster",13);

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
            case "Yukatha":
                return YUKATHA;
            case "Slippers":
                return SLIPPERS;
            case "Bath Robe":
                return BATH_ROBE;
            case "Bath Towel":
                return BATH_TOWEL;
			case "K-Sheet":
                return K_SHEET;
			case "Hand Towel":
                return HAND_TOWEL;	
			case "Bath Mat":
                return BATH_MAT;
			case "Night Wear":
                return NIGHT_WEAR;
			case "Duster":
                return DUSTER;	
            default:
                return null;
        }
    }
}
