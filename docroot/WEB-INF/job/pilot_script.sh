#!/bin/bash

export WEKA_HOME=/opt/weka
export WEKA_JAR=$WEKA_HOME/weka.jar


INPUT_FILTER="" #weka.filters.unsupervised.attribute.ReplaceMissingValues
FOLDS=""
while getopts "f:x:" flag; do
case "$flag" in
    f) INPUT_FILTER=$OPTARG;;
    x) FOLDS=$OPTARG;;
esac
done

INPUT_FILE=${@:$OPTIND:1} #breast-cancer-wisconsin.data
CLASSIFIER=${@:$OPTIND+1:1} #weka.classifiers.bayes.NaiveBayes
INPUT_FILE_ARFF=$INPUT_FILE #'.arff'
PREPROCESED_PREFIX='preprocessed.'
#PREPROCESED_INPUT_FILE_ARFF='preprocessed.'$INPUT_FILE_ARFF
OUTPUT_FILE=weka.out

#
# Following statement produce the simulation_output file
#
OUTFILE=simulation.log
echo "--------------------------------------------------" >  $OUTFILE
echo "Job execution starts on: '"$(date)"'"		  >> $OUTFILE
echo ""                                                   >> $OUTFILE
echo "---[Working directory]----------------------------" >> $OUTFILE
ls -l $(pwd)						  >> $OUTFILE
echo "--------------------------------------------------" >> $OUTFILE
echo "Analisys started at: '"$(date)"'"     	          >> $OUTFILE
echo ""                                                   >> $OUTFILE
echo "Job landed at: '"${HOSTNAME}"'"                     >> $OUTFILE
echo ""                                                   >> $OUTFILE
echo "#################[  START LOG  ]##################" >> $OUTFILE
echo ""                                                   >> $OUTFILE

if [ "$INPUT_FILE" == ""  ]; then
	echo Error: input file missed.			  >> $OUTFILE
	exit 1;
fi

if [ "$CLASSIFIER" == ""  ]; then
	echo Error: classifier missed.			  >> $OUTFILE
	exit 1;
fi

if [ ${INPUT_FILE: -4} != "arff" ]; then
	echo Converting data to arff...			  >> $OUTFILE
	TMP=$(mktemp)
	java -cp $WEKA_JAR weka.core.converters.CSVLoader $INPUT_FILE -H > $TMP 
	cp $TMP $INPUT_FILE'.arff'
	INPUT_FILE_ARFF=$INPUT_FILE_ARFF'.arff'
	rm -f $TMP
else
	echo Input file is already arff. 		  >> $OUTFILE
	INPUT_FILE_ARFF=$INPUT_FILE
fi

echo 'Processing ' $INPUT_FILE_ARFF '...'		  >> $OUTFILE

if [ "$INPUT_FILTER" != "" ]; then
	echo 'Appling "'$INPUT_FILTER'" filter.'	  >> $OUTFILE
	TMP=$(mktemp)
	java -cp $WEKA_JAR $INPUT_FILTER -i $INPUT_FILE_ARFF -o $TMP
	cp $TMP $PREPROCESED_PREFIX$INPUT_FILE_ARFF
	rm -f $TMP
else
	echo 'No filter applied.'			  >> $OUTFILE
	mv  $INPUT_FILE_ARFF $PREPROCESED_PREFIX$INPUT_FILE_ARFF
fi

CHECKALG=$(head -n 1 $PREPROCESED_PREFIX$INPUT_FILE_ARFF  | grep NumericToNominal)
if [ "$CHECKALG" = "" ]; then
	echo  'Appling "weka.filters.unsupervised.attribute.NumericToNominal" filter' >> $OUTFILE
	TMP=$(mktemp)
	java -cp $WEKA_JAR weka.filters.unsupervised.attribute.NumericToNominal -R first-last -i $PREPROCESED_PREFIX$INPUT_FILE_ARFF -o $TMP
	cp $TMP $PREPROCESED_PREFIX$INPUT_FILE_ARFF
	rm -f $TMP
fi

if [ "$FOLDS" != "" ]; then 
	echo  'Using -x ' $FOLDS			  >> $OUTFILE
	FOLDSFLAG="-x $FOLDS"
else
	FOLDSFLAG=""
fi
java -cp $WEKA_JAR $CLASSIFIER -t $PREPROCESED_PREFIX$INPUT_FILE_ARFF $FOLDSFLAG > stdout 2> stderr 
#cat stdout                                                >> $OUTFILE
echo "#################[   END LOG   ]##################" >> $OUTFILE
echo ""                                                   >> $OUTFILE
echo "Simulation ended at: '"$(date)"'"                   >> $OUTFILE
echo "--------------------------------------------------" >> $OUTFILE
echo ""                                                   >> $OUTFILE

#
# Collect all generated output files into a single tar.gz file
#
tar cvfz weka-output.tar.gz $OUTFILE #output/
