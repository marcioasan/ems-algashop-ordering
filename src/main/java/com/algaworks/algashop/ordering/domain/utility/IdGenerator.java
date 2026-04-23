package com.algaworks.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;

import java.util.UUID;

//5.15. Implementando a geração de UUID v7 - 1'
public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator = Generators.timeBasedEpochRandomGenerator();

    private static final TSID.Factory tsidFactory = TSID.Factory.INSTANCE; //6.13. Implementando novos identificadores - 4'50"

    private IdGenerator() {
    }

    public static UUID generateTimeBasedUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }

    //6.13. Implementando novos identificadores - 6'
    /* Variáveis que devem ser criadas quando subir em produção
     * TSID_NODE
     * TSID_NODE_COUNT
     */
    public static TSID generateTSID(){
        return tsidFactory.generate();
    }
}
