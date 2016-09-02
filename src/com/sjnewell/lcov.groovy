package com.sjnewell;

def propertyMissing(name) {
    null
}

def gather(src, output) {
    sh "lcov --capture -b \"${src}\" -d . --output-file \"${output}\" --no-external"
}

def trim(input, output, pattern) {
    sh "lcov -r ${input} \"${pattern}\" -o \"${output}\""
}

def process(input, output) {
    sh "genhtml \"${input}\" -o \"${output}\""
}
