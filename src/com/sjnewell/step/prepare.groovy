package com.sjnewell.step;

def execute(args) {
    if(args.containsKey('prepare')) {
        args.prepare.each{ prepareFn ->
            prepareFn(args)
        }
    }
}
