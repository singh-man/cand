
for dir in */; do
    if [ -f "${dir}pom.xml" ]; then
        echo "🚀 Building ${dir}..."
        mvn -f "${dir}pom.xml" clean install &
    fi
done
wait
