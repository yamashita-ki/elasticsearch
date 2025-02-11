/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.esql.qa.mixed;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakFilters;

import org.elasticsearch.Version;
import org.elasticsearch.test.TestClustersThreadFilter;
import org.elasticsearch.test.cluster.ElasticsearchCluster;
import org.elasticsearch.xpack.esql.qa.rest.EsqlSpecTestCase;
import org.elasticsearch.xpack.ql.CsvSpecReader.CsvTestCase;
import org.junit.ClassRule;

import static org.elasticsearch.xpack.esql.CsvTestUtils.isEnabled;

@ThreadLeakFilters(filters = TestClustersThreadFilter.class)
public class MixedClusterEsqlSpecIT extends EsqlSpecTestCase {
    @ClassRule
    public static ElasticsearchCluster cluster = Clusters.mixedVersionCluster();

    @Override
    protected String getTestRestCluster() {
        return cluster.getHttpAddresses();
    }

    static final Version bwcVersion = Version.fromString(System.getProperty("tests.old_cluster_version"));

    public MixedClusterEsqlSpecIT(String fileName, String groupName, String testName, Integer lineNumber, CsvTestCase testCase) {
        super(fileName, groupName, testName, lineNumber, testCase);
    }

    @Override
    protected void shouldSkipTest(String testName) {
        super.shouldSkipTest(testName);
        assumeTrue("Test " + testName + " is skipped on " + bwcVersion, isEnabled(testName, bwcVersion));
    }
}
