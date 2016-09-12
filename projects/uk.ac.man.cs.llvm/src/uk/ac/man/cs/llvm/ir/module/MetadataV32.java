/*
 * Copyright (c) 2016, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.man.cs.llvm.ir.module;

import java.util.Arrays;

import uk.ac.man.cs.llvm.ir.module.records.MetadataRecord;

public class MetadataV32 extends Metadata {
    public MetadataV32() {
        super();
        idx = 0; // it seem's like there is a different offset of the id in LLVM 3.2 and LLVM 3.8
    }

    @Override
    public void record(long id, long[] args) {
        MetadataRecord record = MetadataRecord.decode(id);
        switch (record) {
            case OLD_NODE:
                createOldNode(args);
                break;

            default:
                super.record(id, args);
                break;
        }
    }

    protected void createOldNode(long[] args) {
        if (args[0] == 0 && args[1] == 3/* && args[2] == 4 && args[3] == 17 */) // 0 3 4 17 = 786688
            System.out.println("!" + idx + " - DW_TAG_auto_variable: name=!" + args[5] + ", type=!" + args[11] + ", lex-block=!" + args[3] + " - " + Arrays.toString(args));
        else if (args[0] == 0 && args[1] == 12/* && args[2] == 4 && args[3] == 9 */)
            // 0 12 4 9 = 786443
            System.out.println("!" + idx + " - DW_TAG_lexical_block" + " - " + Arrays.toString(args));
        else if (args[0] == 0 && args[1] == 6/* && args[2] == 0 && args[3] == 2 */)
            // 0 6 0 2 = 786449
            System.out.println("!" + idx + " - DW_TAG_compile_unit" + " - " + Arrays.toString(args));
        else if (args[0] == 0 && args[1] == 5/* && args[2] == 3 && args[3] == 0 */)
            // 0 5 3 0 = 786449
            System.out.println("!" + idx + " - DW_TAG_base_type: name=!" + args[5] + " - " + Arrays.toString(args));
        else
            System.out.println("!" + idx + " - " + MetadataRecord.OLD_NODE + ": " + Arrays.toString(args));
    }
}
