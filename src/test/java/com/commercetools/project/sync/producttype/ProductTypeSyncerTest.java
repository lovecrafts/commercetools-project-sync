package com.commercetools.project.sync.producttype;

import com.commercetools.sync.producttypes.ProductTypeSync;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductTypeDraftBuilder;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.json.SphereJsonUtils.readObjectFromResource;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ProductTypeSyncerTest {
    @Test
    public void of_ShouldCreateProductTypeSyncerInstance() {
        // preparation
        final SphereClient client = mock(SphereClient.class);

        // test
        final ProductTypeSyncer productTypeSyncer = ProductTypeSyncer.of(client);

        // assertions
        assertThat(productTypeSyncer).isNotNull();
        assertThat(productTypeSyncer.getQuery()).isEqualTo(ProductTypeQuery.of());
        assertThat(productTypeSyncer.getSync()).isInstanceOf(ProductTypeSync.class);
    }

    @Test
    public void transformResourcesToDrafts_ShouldConvertResourcesToDrafts() {
        // preparation
        final SphereClient client = mock(SphereClient.class);
        final ProductTypeSyncer productTypeSyncer = ProductTypeSyncer.of(client);
        final List<ProductType> productTypePage =
            asList(
                readObjectFromResource("product-type-key-1.json", ProductType.class),
                readObjectFromResource("product-type-key-2.json", ProductType.class));

        // test
        final List<ProductTypeDraft> draftsFromPage = productTypeSyncer.transformResourcesToDrafts(productTypePage);

        // assertions
        assertThat(draftsFromPage)
            .isEqualTo(productTypePage.stream()
                                      .map(ProductTypeDraftBuilder::of)
                                      .map(ProductTypeDraftBuilder::build).collect(toList()));
    }
}
