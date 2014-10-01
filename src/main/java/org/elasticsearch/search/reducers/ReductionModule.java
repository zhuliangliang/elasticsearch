/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.reducers;

import com.google.common.collect.Lists;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.multibindings.Multibinder;

import java.util.List;

public class ReductionModule extends AbstractModule {

    private List<Class<? extends Reducer.Parser>> parsers = Lists.newArrayList();

    public ReductionModule() {
//        parsers.add(MetricReductionParser.class);
    }

    /**
     * Enabling extending the get module by adding a custom reducer parser.
     *
     * @param parser The parser for the custom reducer.
     */
    public void addAggregatorParser(Class<? extends Reducer.Parser> parser) {
        parsers.add(parser);
    }

    @Override
    protected void configure() {
        Multibinder<Reducer.Parser> multibinder = Multibinder.newSetBinder(binder(), Reducer.Parser.class);
        for (Class<? extends Reducer.Parser> parser : parsers) {
            multibinder.addBinding().to(parser);
        }
        bind(ReducerParsers.class).asEagerSingleton();
        bind(ReductionParseElement.class).asEagerSingleton();
        bind(ReductionPhase.class).asEagerSingleton();
    }
}