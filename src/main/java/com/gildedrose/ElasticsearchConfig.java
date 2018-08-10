package com.gildedrose;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.File;
import java.io.IOException;

/**
 * Created by Giedrius on 2018-08-09.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.gildedrose.repositories")
public class ElasticsearchConfig {
    private static final String ES_TEMP_DIR_PATH = "embedded_es_tmp_dir";

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws IOException {
        File tmpDir = new File(ES_TEMP_DIR_PATH);
        Settings.Builder elasticsearchSettings =
                Settings.settingsBuilder()
                        .put("http.enabled", "true")
                        .put("index.number_of_shards", "1")
                        .put("path.data", new File(tmpDir, "data").getAbsolutePath())
                        .put("path.logs", new File(tmpDir, "logs").getAbsolutePath())
                        .put("path.work", new File(tmpDir, "work").getAbsolutePath())
                        .put("path.home", tmpDir);


        ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node()
                .client());

        return elasticsearchTemplate;
    }
}
