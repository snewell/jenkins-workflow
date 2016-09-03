// Return flgas that are useful for any build
def usefulFlags() {
    return "-pipe"
}

// Return flags that are useful for debug builds
def debugFlags() {
    return "-ggdb3 -O0"
}

// Return flags that enable additional warnings
def warningFlags() {
    return "-Wall -Wextra -Wshadow -pedantic"
}

// Return flags to enable a sanitizer
//   sanitizer: the sanitizer to enable (one of 'address', 'thread', or
//              'undefined')
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
