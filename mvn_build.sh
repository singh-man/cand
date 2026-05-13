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
failed_modules=()
pids=()
pid_modules=()

for dir in */; do
    if [ -f "${dir}pom.xml" ]; then
        module="${dir%/}"
        echo "🚀 Building ${module}..."
        if [ "$parallel" = true ]; then
            mvn -f "${dir}pom.xml" clean install &
            pids+=("$!")
            pid_modules+=("$module")
        else
            if ! mvn -f "${dir}pom.xml" clean install; then
                status=1
                failed_modules+=("$module")
            fi
        fi
    fi
done

if [ "$parallel" = true ]; then
    for i in "${!pids[@]}"; do
        if ! wait "${pids[$i]}"; then
            status=1
            failed_modules+=("${pid_modules[$i]}")
        fi
    done
fi

if [ "${#failed_modules[@]}" -gt 0 ]; then
    echo
    echo "Failed modules:"
    printf ' - %s\n' "${failed_modules[@]}"
fi

exit "$status"
