package com.gpuntd.adminapp.Interface;

import com.google.ads.consent.ConsentStatus;

public interface AdConsentListener {
    void onConsentUpdate(ConsentStatus consentStatus);
}
