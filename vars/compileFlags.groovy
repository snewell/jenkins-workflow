def usefulFlags() {
    return "-pipe"
}

def debugFlags() {
    return "-ggdb3 -O0"
}

def warningFlags() {
    return "-Wall -Wextra -Wshadow -pedantic"
}

def sanitizerFlags(sanitizer) {
    echo "sanitizer = ${sanitizer}"
    if(sanitizer == "address") {
        return "-fsanitize=address"
    }
    if(sanitizer == "thread") {
        return "-fsanitize=thread"
    }
    if(sanitizer == "undefined") {
        return "-fsanitize=undefined"
    }
}
