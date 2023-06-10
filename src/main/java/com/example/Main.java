package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.configuration.Configuration;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.apache.fop.svg.AbstractFOPTranscoder;
import org.apache.fop.svg.PDFTranscoder;


public class Main {

    private static Logger logger = Logger.getLogger("main");

    public static void main(String[] argv) throws Exception {
        File in = new File("test.svg");
        logger.info("test PlantUML method");
        Main.testPlantUmlMethod(in, new File("test-1.pdf"));
        logger.info("test fop");
        Main.testFop(in, new File("test-2.pdf"));
        logger.info("test fop with config");
        Main.testFopWithConfig(in, new File("test-3.pdf"));
    }

    private static void testPlantUmlMethod(File in, File out) throws SVGConverterException {
        SVGConverter converter = new SVGConverter();
        converter.setDestinationType(DestinationType.PDF);
        converter.setSources(new String[] { in.getAbsolutePath() });
        converter.setDst(out);
        converter.execute();
    }

    private static void testFop(File in, File out) throws TranscoderException, FileNotFoundException {
        PDFTranscoder transcoder = new PDFTranscoder();
        TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(in));
        TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(out));
        transcoder.transcode(transcoderInput, transcoderOutput);
    }

    private static void testFopWithConfig(File in, File out) throws TranscoderException, FileNotFoundException, ConfigurationException {
        File config = new File("fop.xconf");
        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        Configuration cfg = cfgBuilder.buildFromFile(config);

        AbstractFOPTranscoder transcoder = new PDFTranscoder();
        transcoder.configure(cfg);

        TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(in));
        TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(out));
        transcoder.transcode(transcoderInput, transcoderOutput);
    }
}
