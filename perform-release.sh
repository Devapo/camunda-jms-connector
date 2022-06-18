#!/usr/bin/env bash
set -e

VERSION=$1
TAG_NAME="v${VERSION}"

if [[ ${VERSION} == *SNAPSHOT ]]
then
  echo "Release version must not be SNAPSHOT version";
fi

# Prepare release
mvn versions:set -DnewVersion="${VERSION}" -DgenerateBackupPoms=false
mvn clean package
git add -A
git commit -m "[${VERSION}] Prepare for release"
git tag "${TAG_NAME}"

# Perform release
mvn clean deploy -DskipTests
git push

# Prepare development iteration
mvn versions:set -DnextSnapshot
git add -A
git commit -m "Prepare for next development iteration"
git push
