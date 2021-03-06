/*
 * Copyright 2011 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.set.mutable;

import com.gs.collections.impl.factory.Sets;
import com.gs.collections.impl.test.Verify;
import org.junit.Test;

public class UnmodifiableMutableSetTest
{
    @Test
    public void serializedForm()
    {
        Verify.assertSerializedForm(
                "rO0ABXNyAFNjb20uZ3MuY29sbGVjdGlvbnMuaW1wbC5jb2xsZWN0aW9uLm11dGFibGUuVW5tb2Rp\n"
                        + "ZmlhYmxlQ29sbGVjdGlvblNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBzcgAuY29tLmdz\n"
                        + "LmNvbGxlY3Rpb25zLmltcGwuc2V0Lm11dGFibGUuVW5pZmllZFNldAAAAAAAAAABDAAAeHB3CAAA\n"
                        + "AAA/QAAAeHg=",
                UnmodifiableMutableSet.of(Sets.mutable.of()));
    }
}
