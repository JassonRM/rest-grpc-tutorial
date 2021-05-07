CONTEXT_ROOT=$1
WAR=$2
CLEAN=false

while getopts :c opt; do
    case $opt in
        c) clean=true ;;
        \?) echo "Unknown option -$OPTARG"; exit 1;
    esac
done

if [ "$CLEAN" = true ]
then
    mvn clean

    cd ./glassfish5/bin
    ./asadmin undeploy "$CONTEXT_ROOT"
    ./asadmin stop-domain
else
    if [ -d "./glassfish5" ]
    then
        echo "Glassfish directory already exists"
    else
        wget http://download.oracle.com/glassfish/5.0.1/nightly/latest-glassfish.zip
        unzip latest-glassfish.zip
        rm latest-glassfish.zip
    fi

    mvn package

    cd ./glassfish5/bin
    ./asadmin start-domain
    ./asadmin undeploy "$CONTEXT_ROOT"
    ./asadmin deploy --name "$CONTEXT_ROOT" --contextroot "$CONTEXT_ROOT" "$WAR"
fi



#must use jdk 8
#sudo apt-get install openjdk-8-jdk
#sudo update-alternatives --config java


