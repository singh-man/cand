#!/usr/bin/env bash

usage() {
    cat <<EOF
Usage: $0 [-p] [-h|--help]

Build all Maven projects in immediate child directories.

Options:
  -p          Build projects in parallel.
  -h, --help  Show this help message.
EOF
}

parallel=false

while [ "$#" -gt 0 ]; do
    case "$1" in
        -p)
            parallel=true
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        *)
            echo "Unknown option: $1" >&2
            usage >&2
            exit 1
            ;;
    esac
    shift
done

status=0
pids=""

for dir in */; do
    if [ -f "${dir}pom.xml" ]; then
        echo "🚀 Building ${dir}..."
        if [ "$parallel" = true ]; then
            mvn -f "${dir}pom.xml" clean install &
            pids="$pids $!"
        else
            mvn -f "${dir}pom.xml" clean install || status=1
        fi
    fi
done

if [ "$parallel" = true ]; then
    for pid in $pids; do
        wait "$pid" || status=1
    done
fi

exit "$status"
