#!/bin/sh

# =============================
# ALEXANDER STRADNIC: 119377263
# =============================

REPO=repo # top-level repository directory

# ===========
rm -rf ${REPO} # remove repository if it exists
mkdir ${REPO} # create reposity directory
cd ${REPO} # move into the repository’s directory
git init --quiet # initialise the repository
# ===========



# 1 ===========
touch recorder
git add recorder

git commit -m "Added file 'recorder' to 'master'" --quiet



# 2 ===========
SHA1=`git log --oneline | sed -n -e '1s/ .*//p'`



# 3 ===========
git branch api --quiet
git checkout api --quiet

echo "introduce create() and push() in api branch" >> recorder

git add recorder
git commit -m "Added create() and push() handles to 'api'" --quiet



# 4 ===========
git checkout master --quiet
echo "compile empty main() in main branch" >> recorder

git add recorder
git commit -m "Added main() to 'master'" --quiet



# 5 ===========
git branch dev api --quiet



# 6 ===========
git checkout dev --quiet
echo "implement create() and push() in dev branch" >> recorder

git add recorder
git commit -m "Implemented create() and push() handles in 'dev'" --quiet



# 7 ===========
git checkout master --quiet
echo "merged 'api' with 'master'" >> recorder
git merge -s ours api --quiet



# 8 ===========
echo "compile main with create()\
 and push() in master branch" >> recorder

git add recorder
git commit -m "Compiled main with \
 create() and push() handles in 'master'" --quiet



# 9 ===========
echo "merged 'dev' with 'master'" >> recorder
git merge -s dev --quiet



# 10 ===========
echo "running main with create()\
 and push() in master branch" >> recorder

git add recorder
git commit -m "Ran main with create() and push() in 'master'" --quiet



# 11 ===========
git checkout api --quiet

echo "introduce pop() in api branch" >> recorder

git add recorder
git commit -m "Added pop() handle to 'api'" --quiet



# 12 ===========
git checkout dev --quiet
echo "merged 'api' with 'dev'" >> recorder
git merge -s ours api --quiet



# 13 ===========
echo "implement pop() in dev branch" >> recorder

git add recorder
git commit -m "Implemented pop() in 'dev'" --quiet



# 14 ===========
git checkout master --quiet
echo "merged 'dev' with 'master'" >> recorder
git merge -s ours dev --quiet



# 15 ===========
echo "running main with push() and pop() in master branch" >> recorder

git add recorder
git commit -m "Ran main with push() and pop() in 'master'" --quiet



# 16 ===========
git checkout dev --quiet
echo "BUG: push() returns empty list" >> recorder

git add recorder
git commit -m "Bug with push() method in 'dev'" --quiet



# 17 ===========
echo "development" >> recorder

git add recorder
git commit -m "development in 'dev'" --quiet

# 18 ===========
echo "development (2)" >> recorder

git add recorder
git commit -m "more development in 'dev'" --quiet

# 19 ===========
echo "development (3)" >> recorder

git add recorder
git commit -m "even more development in 'dev'" --quiet



# 20 ===========
ERROR=`grep BUG recorder`

git bisect start
git bisect bad
git bisect good "$SHA1"

git bisect good

git bisect bad

git bisect good

git bisect reset

# 21 ===========
grep -v BUG recorder > temp
mv temp recorder

echo "fixed BUG in 'dev'" >> recorder
git stage recorder
git commit -m "fixed BUG in 'dev'" --quiet



# 22 ===========
git checkout master --quiet
echo "merged dev into master" >> recorder
git merge -s ours dev -m "merged dev into master" --quiet



# 23 ===========
echo "running main with create(), push(), and pop()\
 in 'master'" >> recorder
git stage recorder
git commit -m "Ran main with create(), push(), and pop() in\
 'master'" --quiet



# 24 ===========
git log --oneline --graph >> recorder