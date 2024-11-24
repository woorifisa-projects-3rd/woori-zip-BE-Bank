package fisa.woorizip.bank.spendinghistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String TOT_USE_AM;
    private String CRDSL_USE_AM;
    private String CNF_USE_AM;
    private String INTERIOR_AM;
    private String INSUHOS_AM;
    private String OFFEDU_AM;
    private String TRVLEC_AM;
    private String FSBZ_AM;
    private String SVCARC_AM;
    private String DIST_AM;
    private String PLSANIT_AM;
    private String CLOTHGDS_AM;
    private String AUTO_AM;
    private String FUNITR_AM;
    private String APPLNC_AM;
    private String HLTHFS_AM;
    private String BLDMNG_AM;
    private String ARCHIT_AM;
    private String OPTIC_AM;
    private String AGRICTR_AM;
    private String LEISURE_S_AM;
    private String LEISURE_P_AM;
    private String CULTURE_AM;
    private String SANIT_AM;
    private String INSU_AM;
    private String OFFCOM_AM;
    private String BOOK_AM;
    private String RPR_AM;
    private String HOTEL_AM;
    private String GOODS_AM;
    private String TRVL_AM;
    private String FUEL_AM;
    private String SVC_AM;
    private String DISTBNP_AM;
    private String DISTBP_AM;
    private String GROCERY_AM;
    private String HOS_AM;
    private String CLOTH_AM;
    private String RESTRNT_AM;
    private String AUTOMNT_AM;
    private String AUTOSL_AM;
    private String KITWR_AM;
    private String FABRIC_AM;
    private String ACDM_AM;
    private String MBRSHOP_AM;

}
