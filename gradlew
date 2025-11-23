#!/usr/bin/env sh

#
# Copyright 2015-present Facebook, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
#

# Stop on error
set -e

#
# Helper script to gracefully kill a process and its children.
#
# On Linux, we use a feature of the kernel known as "process groups"
# to ensure we kill the process and all of its children.
#
# On other platforms, we just kill the parent process and hope for
# the best.
#
kill_process_and_children() {
  if [ $(uname) == "Linux" ]; then
    kill -9 -$1
  else
    kill -9 $1
  fi
}

#
# Locate and run the real gradle wrapper.
#
# We need to find the real gradle wrapper, but it might be in a
# different location depending on the version of React Native, so we
# search for it.
#
# We also need to be careful to use the correct absolute path for the
# wrapper, so we can't just use `find`.
#
for i in $(seq 1 5); do
    if [ -x "android/gradlew" ]; then
        GRADLEW="android/gradlew"
        break
    fi

    if [ -x "gradlew" ]; then
        GRADLEW="gradlew"
        break
    fi
    cd ..
done

if [ -z "$GRADLEW" ]; then
    echo "Could not find gradlew"
    exit 1
fi

#
# The gradle wrapper is a shell script which will start a JVM.
#
# We're going to start the wrapper in the background, and then tail
# the logs. We'll also set up a trap to kill the wrapper and all of its
# children when this script is killed.
#
# We'll use this weird `if` statement to get the process ID of the
# wrapper. We can't use `$!` because the wrapper script will exit
# immediately, and we'll get the PID of the `tail` command.
#
if "$GRADLEW" "$@" &
then
    GRADLE_PID=$!
    trap "kill_process_and_children $GRADLE_PID" EXIT
    wait $GRADLE_PID
fi
