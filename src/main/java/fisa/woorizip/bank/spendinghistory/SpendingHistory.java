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
    private double TOT_USE_AM;
    private double CRDSL_USE_AM;
    private double CNF_USE_AM;
    private double INTERIOR_AM;
    private double INSUHOS_AM;
    private double OFFEDU_AM;
    private double TRVLEC_AM;
    private double FSBZ_AM;
    private double SVCARC_AM;
    private double DIST_AM;
    private double PLSANIT_AM;
    private double CLOTHGDS_AM;
    private double AUTO_AM;
    private double FUNITR_AM;
    private double APPLNC_AM;
    private double HLTHFS_AM;
    private double BLDMNG_AM;
    private double ARCHIT_AM;
    private double OPTIC_AM;
    private double AGRICTR_AM;
    private double LEISURE_S_AM;
    private double LEISURE_P_AM;
    private double CULTURE_AM;
    private double SANIT_AM;
    private double INSU_AM;
    private double OFFCOM_AM;
    private double BOOK_AM;
    private double RPR_AM;
    private double HOTEL_AM;
    private double GOODS_AM;
    private double TRVL_AM;
    private double FUEL_AM;
    private double SVC_AM;
    private double DISTBNP_AM;
    private double DISTBP_AM;
    private double GROCERY_AM;
    private double HOS_AM;
    private double CLOTH_AM;
    private double RESTRNT_AM;
    private double AUTOMNT_AM;
    private double AUTOSL_AM;
    private double KITWR_AM;
    private double FABRIC_AM;
    private double ACDM_AM;
    private double MBRSHOP_AM;

}
