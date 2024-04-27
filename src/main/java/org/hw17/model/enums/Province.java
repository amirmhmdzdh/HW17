package org.hw17.model.enums;

public enum Province {
    AZARBAYJAN_SHARGHI(true),
    AZARBAYJAN_GHARBI(false),
    ARDABIL(false),
    ISFAHAN(true),
    ALBORZ(true),
    ILAM(false),
    BOOSHEHR(false),
    TEHRAN(true),
    CHAHARMAHAL_VA_BAKHTIARY(false),
    KHORASAN_JONOOBI(false),
    KHORASAN_RAZAVI(true),
    KHORASAN_SHOMALI(false),
    KHOOZESTAN(true),
    ZANJAN(false),
    SEMNAN(false),
    SISTAN_VA_BALOOCHESTAN(false),
    FARS(true),
    GHAZVIN(false),
    GHOM(true),
    KORDESTAN(false),
    KERMAN(false),
    KERMANSHAH(false),
    KOHGELOOYE_VA_BOYERAHMAD(false),
    GOLESTAN(false),
    GILAN(true),
    LORESTAN(false),
    MAZANDARAN(false),
    MARKAZI(false),
    HORMOZGAN(false),
    HAMEDAN(false),
    YAZD(false);

    final boolean isMetropolis;

    Province(boolean isMetropolis){
        this.isMetropolis = isMetropolis;
    }

    public boolean isMetropolis() {
        return isMetropolis;
    }
}