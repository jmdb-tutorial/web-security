package jmdbtutorial.websecurity.openam.resourceserver_a;

import com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration;
import com.bazaarvoice.dropwizard.assets.AssetsConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ResourceServer_A_Configuration extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }
}