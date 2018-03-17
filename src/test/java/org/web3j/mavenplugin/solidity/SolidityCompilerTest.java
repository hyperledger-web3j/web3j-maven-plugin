package org.web3j.mavenplugin.solidity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Test;

public class SolidityCompilerTest {

    private SolidityCompiler solidityCompiler;

    @Test
    public void compileContract() throws Exception {
        Set<String> source = Collections.singleton("src/test/resources/Greeter.sol");

        CompilerResult compilerResult = solidityCompiler.compileSrc("src/test/resources/", source, SolidityCompiler.Options.ABI, SolidityCompiler.Options.BIN);

        assertFalse(compilerResult.errors, compilerResult.isFailed());
        assertTrue(compilerResult.errors, compilerResult.errors.isEmpty());
        assertFalse(compilerResult.output.isEmpty());

        assertTrue(compilerResult.output.contains("\"src/test/resources/Greeter.sol:greeter\""));
    }

    @Before
    public void loadCompiler() {
        solidityCompiler = SolidityCompiler.getInstance(new SystemStreamLog());
    }

    @Test
    public void invalidContractVersion() throws Exception {
        Set<String> source = Collections.singleton("src/test/resources/Greeter-invalid-version.sol");

        CompilerResult compilerResult = solidityCompiler.compileSrc("src/test/resources/", source, SolidityCompiler.Options.ABI, SolidityCompiler.Options.BIN);

        assertTrue(compilerResult.isFailed());
        assertFalse(compilerResult.errors.isEmpty());
        assertTrue(compilerResult.output.isEmpty());
    }

    @Test
    public void invalidContractSyntax() throws Exception {
        Set<String> source = Collections.singleton("src/test/resources/Greeter-invalid-syntax.sol");

        CompilerResult compilerResult = solidityCompiler.compileSrc("src/test/resources/", source, SolidityCompiler.Options.ABI, SolidityCompiler.Options.BIN);

        assertTrue(compilerResult.isFailed());
        assertFalse(compilerResult.errors.isEmpty());
        assertTrue(compilerResult.output.isEmpty());
    }
}