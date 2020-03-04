#!/bin/bash

LOCAL_ADDRESS="project"

if [ -e $LOCAL_ADDRESS ]
then
  rm -rf $LOCAL_ADDRESS
else
  mkdir $LOCAL_ADDRESS
fi
#################  read CLONE_ADDRESS from screen################  
#echo -n "please input clone address:"
#read PERSON
#ADDRESS=$PERSON

#################  lone project ################  
#git clone $ADDRESS $CLONE_ADDRESS
git clone $1 $LOCAL_ADDRESS

cd $LOCAL_ADDRESS


################ get lines which was changed ################  
difflines() {
    local hash=
    local path=
    local line=
    local context=
    while read; do
        esc=$'\033'

	#if [[ $REPLY =~ index\ ([^[:blank:]]+).* ]];then
	#    hash=${BASH_REMATCH[1]}

	if [[ $REPLY =~ index\ ([a-zA-Z0-9]+)..([a-z0-9]+).* ]];then
	    hash=${BASH_REMATCH[2]}
        elif [[ $REPLY =~ ---\ (a/)?.* ]]; then
            continue
        elif [[ $REPLY =~ \+\+\+\ (b/)?([^[:blank:]$esc]+).* ]]; then
            path=${BASH_REMATCH[2]}

        elif [[ $REPLY =~ @@\ -[0-9]+(,[0-9]+)?\ \+([0-9]+)(,[0-9]+)?\ @@.* ]]; then
            line=${BASH_REMATCH[2]}
        elif [[ $REPLY =~ ^($esc\[[0-9;]+m)*([\ +-]) ]]; then
            context=${BASH_REMATCH[1]}
            echo "$hash,$path,$line,$REPLY"
            if [[ ${BASH_REMATCH[2]} != - ]]; then
                ((line++))
            fi
        fi
    done

}


difflines2() {
    local hash=
    local path=
    local line=
    while read; do
        esc=$'\033'

	if [[ $REPLY =~ index\ ([^[:blank:]$esc]+)([0-200000]+) ]];then
	    hash=${BASH_REMATCH[1]}

        elif [[ $REPLY =~ ---\ (a/)?.* ]]; then
            continue
        elif [[ $REPLY =~ \+\+\+\ (b/)?([^[:blank:]$esc]+).* ]]; then
            path=${BASH_REMATCH[2]}

        elif [[ $REPLY =~ @@\ -[0-9]+(,[0-9]+)?\ \+([0-9]+)(,[0-9]+)?\ @@.* ]]; then
            line=${BASH_REMATCH[2]}
        elif [[ $REPLY =~ ^($esc\[[0-9;]+m)*([\ +-]) ]]; then
            echo "$hash,$path,$line,$REPLY"
            if [[ ${BASH_REMATCH[2]} != - ]]; then
                ((line++))
            fi
        fi
    done

}

############# ### Traversing files to get all files with path################ 
function getdir(){
    for element in `ls $1`
    do  
        dir_or_file=$1"/"$element
        if [ -d $dir_or_file ]
        then 
            getdir $dir_or_file
        else
            echo $dir_or_file >> $output_file
        fi  
    done
}

input_dir="."
output_file="filename.txt"
getdir $input_dir


################  store log information into a text file ################  
git log > projectLog.txt


################  pick out commit id and store them into a text file ################  
grep -i "commit" -A 0 projectLog.txt | grep -v  -e  "--" >commitHash.txt

################  further screening ################ 
sed -r '/^\ /d' commitHash.txt >commitHash2.txt

line=`sed -n '$=' commitHash2.txt`
echo $line

################  store all commit id into a array ################  
j=0
while read line
do
    i=2
    while :
    do
        str=` echo $line | cut -d " " -f $i `
        #echo $i, $str
        if [ "$str" == "$line" ];then
            arr[j]=$str
            break
        elif [ "$str" != "" ];then
            arr[j]=$str
        else
            break
        fi
        i=$(($i+2))
        j=$(($j+1))
    done
done < commitHash2.txt



COMMIT1=${arr[0]}
COMMIT2=${arr[$line-1]}


echo COMMIT1=$COMMIT1
echo COMMIT2=$COMMIT2
#echo filename= $FILENAME

git diff $COMMIT1 $COMMIT2 > gitdiff1.txt
git diff $COMMIT1 $COMMIT2 | difflines > gitdiff2.txt
git diff -U0 $COMMIT1 $COMMIT2 | difflines > gitdiff3.txt
#git diff -U0 $COMMIT1 $COMMIT2 | difflines > gitdiff4.csv


sed -i "s/:/,/g" gitdiff3.txt
cat gitdiff3.txt > gitdiff4.csv



rm -f gitdiff1.txt
rm -f gitdiff2.txt
rm -f gitdiff3.txt
rm -f commitHash.txt
rm -f filename.txt
rm -f commitHash2.txt
rm -f projectLog.txt
