package com.sjnewell.step;

def call(args) {
    if(args.containsKey('prepare')) {
        args.prepare.each{ prepareFn ->
            prepareFn(args)
        }
    }
}
