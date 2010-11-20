// GPars - Groovy Parallel Systems
//
// Copyright © 2008-10  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package groovyx.gpars.actor.impl

import groovyx.gpars.actor.DynamicDispatchActor
import org.codehaus.groovy.runtime.NullObject

/**
 * Represents the DDA closure to invoke appropriate message handlers based on message runtime type
 *
 * @author Vaclav Pech
 */
public final class DDAClosure extends Closure {
    private static final long serialVersionUID = 7644465423857532479L;

    private final def dda

    def DDAClosure(final DynamicDispatchActor dda) {
        super(dda);
        this.dda = dda
    }

    @SuppressWarnings("GroovyConditionalCanBeElvis")
    @Override
    Object call(Object msg) {
        return dda.onMessage(msg != null ? msg : NullObject.nullObject)  //Groovy truth won't let us use Elvis for numbers, strings and collections correctly)
    }
}
