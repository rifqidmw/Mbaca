package com.thelazyproject.mbaca.core.security

import okhttp3.CertificatePinner

object SSLCertificatePinner {

    fun getCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(
                "gutendex.com",
                "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=",
                "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M="
            )
            .add(
                "*.gutendex.com",
                "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=",
                "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M="
            )
            .build()
    }
}

