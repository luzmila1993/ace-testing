#!/bin/sh

#PARAMETERS
SOURCEFILE=$1
OUTPUTFILE=$2

#ARRAYS INPUTS
LABELS=("tests=" "failures=" "skipped=" "time=")
RESULT_TYPES=(PASSED FAILED ABORTED TIME\(secs\))

#VARIABLE INITIALIZATION
NUMBEROFTESTS=0
INDEX=0

#FILE HEADER
TESTSUITENAME=$(grep "\:$" ${SOURCEFILE} | head -n 2 | tail -n +2 | sed 's/.$//' | sed -e 's/^[[:space:]]*//')
DISPLAYNAME=$(grep "\:$" ${SOURCEFILE} | head -n 2 | tail -n +2 | cut -d '.' -f 2 | sed 's/.$//')
HEADER='<testsuite name="'${TESTSUITENAME}'" displayname="'${DISPLAYNAME}'"'

for RESULTTYPE in ${RESULT_TYPES[@]}
do
    NUMBEROFTESTS=$(grep ${RESULTTYPE} ${SOURCEFILE} | cut -d ':' -f 2 | tail -1)
        if [[  ${RESULTTYPE} != TIME\(secs\) ]]; then
            TOTALTESTS=`expr ${TOTALTESTS} + ${NUMBEROFTESTS}`
        fi

    HEADER=$HEADER" "${LABELS[${INDEX}]}'"'$NUMBEROFTESTS'"'
    INDEX=$INDEX+1
done

echo '<?xml version="1.0" encoding="UTF-8"?>' > ${OUTPUTFILE}
echo $HEADER'>' >> ${OUTPUTFILE}

#FILE BODY
ABORTED=$(grep ABORTED ${SOURCEFILE} | cut -d ':' -f 2- | tail -1)
for i in $(seq 1 ${TOTALTESTS});
do
    TESTCASENAME=$(grep STARTING ${SOURCEFILE} | cut -d ':' -f 4- | sed 's/..$//' | head -n $i | tail -n +$i)
    RESULT=$(grep FINISHED ${SOURCEFILE} | cut -d ':' -f 4- | head -n $i | tail -n +$i)
    echo "<testcase name=\"${TESTCASENAME}\" classname=\"${TESTCASENAME}\" displayname=\"${TESTCASENAME}()\">" >> ${OUTPUTFILE}
        if [[ "${RESULT}" == *"FAILED"* ]]; then
        FAIL_MSG=$(sed -n '/Expected/ {n;p}' ${SOURCEFILE} | cut -c 11- | head -n ${i} | tail -n 1)
        echo "<failure>${FAIL_MSG}</failure>" >> ${OUTPUTFILE}
        fi
        if [[ "${RESULT}" == *"ABORTED"* ]]; then
        ABORT_MSG=$( grep Aborting TEST-1.xml | tail -${ABORTED}  | head -n ${i} | tail -n 1)
        echo "<skipped>${ABORT_MSG}</skipped>" >> ${OUTPUTFILE}
        fi
    echo "</testcase>" >> ${OUTPUTFILE}
done

#FILE FOOTER
echo '</testsuite>' >> ${OUTPUTFILE}