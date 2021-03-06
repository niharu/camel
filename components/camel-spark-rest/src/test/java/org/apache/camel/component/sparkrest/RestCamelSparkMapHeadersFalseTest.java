/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.sparkrest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

public class RestCamelSparkMapHeadersFalseTest extends BaseSparkTest {

    @Test
    public void testSparkMapHeadersFalse() throws Exception {
        getMockEndpoint("mock:foo").expectedMessageCount(1);
        getMockEndpoint("mock:foo").message(0).header(Exchange.HTTP_PATH).isNull();

        String out2 = template.requestBodyAndHeader("http://localhost:" + getPort() + "/hello", null, "Accept", "application/json", String.class);
        assertEquals("{ \"reply\": \"Bye World\" }", out2);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                restConfiguration().component("spark-rest").componentProperty("mapHeaders", "false");

                rest("/hello")
                    .get().consumes("application/json")
                        .route()
                        .to("mock:foo")
                        .transform().constant("{ \"reply\": \"Bye World\" }");
            }
        };
    }
}
