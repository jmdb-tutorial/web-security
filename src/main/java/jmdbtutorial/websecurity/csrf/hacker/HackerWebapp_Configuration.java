package jmdbtutorial.websecurity.csrf.hacker;

import com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration;
import com.bazaarvoice.dropwizard.assets.AssetsConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HackerWebapp_Configuration extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();

    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }
}