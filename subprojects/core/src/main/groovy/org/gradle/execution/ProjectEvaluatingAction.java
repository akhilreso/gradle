/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.execution;

import org.gradle.api.internal.project.ProjectInternal;

import java.util.List;

/**
 * Ensures that projects resolved from the command line task names are evaluated.
 *
 * by Szczepan Faber, created at: 11/22/12
 */
public class ProjectEvaluatingAction implements BuildConfigurationAction {

    private final TaskPathProjectEvaluator evaluator;

    public ProjectEvaluatingAction(TaskPathProjectEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void configure(BuildExecutionContext context) {
        List<String> taskNames = context.getGradle().getStartParameter().getTaskNames();
        ProjectInternal project = context.getGradle().getDefaultProject();

        for (String path : taskNames) {
            evaluator.evaluateByPath(project, path);
        }
        context.proceed();
    }
}