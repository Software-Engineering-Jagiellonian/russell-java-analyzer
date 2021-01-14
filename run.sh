log_dir="/var/log/frege"
mkdir -p "$log_dir"

trap 'touch "$log_dir/kill_pill"; kill -TERM $PID' TERM INT
java -jar "/app/frege-java-analyzer-0.0.1-SNAPSHOT.jar" ${JAVA_OPTS}
    >> "$log_dir/app.out"
    2>> "$log_dir/app.err" &
PID=$!
wait $PID
trap - TERM INT
wait $PID
EXIT_STATUS=$?

touch "$log_dir/kill_pill";