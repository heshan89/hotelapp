package com.hotel.dao.enums;

public enum ItemEnum {

    S_SHEET("S-Sheet", 1, "SS"),
    D_SHEET("D-Sheet", 2,"DS"),
    K_SHEET("K-Sheet", 3,"K"),
    PILLOW_CASE("Pillow Case", 4,"PC"),
    SHIN_PC("shin-PC", 5,"新PC"),
    BATH_TOWEL("Bath Towel", 6,"BT"),
    HAND_TOWEL("Hand Towel", 7,"HT"),
    WASH("Wash", 8,"WT"),
    BATH_MAT("Bath Mat", 9,"BM"),
    CLEANER("Cleaner", 10,"CT"),
    YUKATHA("Yukatha", 11,"浴衣"),
    BATH_ROBE("Bath robe", 12,"BR"),
    NIGHT_WEAR("Night Wear", 13,"ナイトウェア"),
    DUSTER("Duster", 14,"ダスター"),
    SLIPPERS("Slipper", 15,"スリッパ"),
    PAJAMAS_M("Pajamas M", 16,"ﾊﾟｼﾞｬﾏM"),
    PAJAMAS_L("Pajamas L", 17,"ﾊﾟｼﾞｬﾏL");

	private ItemEnum (String code, Integer value, String shortCode) {
		this.code = code;
		this.value = value;
        this.shortCode = shortCode;
	}
	
    private String code;
    private Integer value;
    private String shortCode;

    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }

    public String getShortCode() {
        return shortCode;
    }

    public static ItemEnum getEnum(String code) {
        switch (code) {
            case "S-Sheet":
                return S_SHEET;
            case "D-Sheet":
                return D_SHEET;
            case "K-Sheet":
                return K_SHEET;
            case "Pillow Case":
                return PILLOW_CASE;
            case "shin-PC":
                return SHIN_PC;
            case "Bath Towel":
                return BATH_TOWEL;
            case "Hand Towel":
                return HAND_TOWEL;
            case "Wash":
                return WASH;
			case "Bath Mat":
                return BATH_MAT;
			case "Cleaner":
                return CLEANER;
			case "Yukatha":
                return YUKATHA;
			case "Bath robe":
                return BATH_ROBE;
			case "Night Wear":
                return NIGHT_WEAR;
            case "Duster":
                return DUSTER;
            case "Slipper":
                return SLIPPERS;
            case "Pajamas M":
                return PAJAMAS_M;
            case "Pajamas L":
                return PAJAMAS_L;
            default:
                return null;
        }
    }

    public static ItemEnum[] getSuiteOrderItems() {
        return new ItemEnum[]{BATH_TOWEL, HAND_TOWEL, WASH, PAJAMAS_M, PAJAMAS_L};
    }
}
