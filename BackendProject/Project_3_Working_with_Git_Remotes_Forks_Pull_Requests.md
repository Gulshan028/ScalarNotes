# Lecture | Working with Git Remotes, Forks, Pull Requests

## I] Git Remotes:
The three parts, viz. working directory, staging area and local git repository are part of 
local machine. They aren't present online for collaboration. Here, we need a cloud based
repositories which can host. Thus, Git remotes play an important role here in doing so.

### 1.1) Demo:

1. Make a GitHub account and create an online repository on it. 
2. Now, we need to connect local repository to online repository. We can connect our local
   project repository to multiple online repositories. This is to avoid a single 
   point of failure in case there are some unwanted issues with one of the online repositories. 
   However, we need to name the first one as 'origin'. It is the convention to call the 
   first remote repository of the project as origin.
3. **Link Your Local Repository to the Remote:**
   To connect your local Git repository to the remote GitHub repository, use the 
   `git remote add origin <remote_repository_link>` command.
   Here, `origin` is the name given to the remote repository, and the URL points to 
   the location of the repository on GitHub.
   Here, the name 'origin' and the link act as a key-value pair. In further uses, we will
   use 'origin' instead of the link everywhere as using link is comparatively cumbersome.
4. **Push Your Local Project to GitHub:**
   Once the remote is set up, you can push your local branch to the remote repository 
   using the `git push` command.
   - The command is: `git push origin main`.
   - It means push the main branch to the remote repo 'origin'.
   - One thing to note is no matter which branch you push, only the committed files will 
     be pushed to remote repo. If some files are untracked or modified, i.e. uncommitted work 
     in simple word, won't be pushed. 
   - Once you push your local git repo to the remote repo then on GitHub, you can see all the
     commits. Also, you can see all the details of each commit, along with author name, 
     commit time, files' previous state(content) and the current content along with
     highlighting delta. 
5. My doubt:
   `git push origin main` will push the committed files of the main branch to the 
   remote repo `origin`. But for this to happen does the current branch of my local 
   project need to be `main` or can I push the `main` branch even if I am on some other
   branch?
   - Answer:
     No—your current local branch does not have to be main; `git push origin main` will
     push the commits from your local `main` branch to origin/main regardless of which
     branch you have checked out, as long as a local main exists.  This works because 
     `git push <remote> <branch>` targets the named reference explicitly and does not 
     depend on the currently checked-out branch.

   ### 5.1) How it works
   `git push origin main` is effectively a refspec of main:main, meaning “push local 
refs/heads/main to origin’s refs/heads/main.”  By contrast, running git push with no
branch name pushes the current branch to its configured upstream (or errors if none), 
so specifying main removes any dependence on your current checkout.  If you also want
to set tracking for main, run `git push -u origin main once` so future `git push` 
from main knows its upstream. 

   ### 5.2) Common commands
   - `git push origin main` :- pushes main (works from any branch)
   - `git push origin feature-branch` :- pushes feature-branch (works from any branch)
   - `git push` :- pushes the **current** branch to its configured upstream
   - `git push -u origin main` :- Create origin/main from your local main and set upstream.
   - Only committed changes are pushed; uncommitted working tree changes do not affect 
  what gets sent to the remote.

   ### 5.3) Example:

    ```bash
    # You're on feature-branch
    git branch
    * feature-branch
      main
    
    # This still works - pushes main to origin
    git push origin main
    ```
---

## II] Experiment:
I have a remote repo on the GitHub with the name 'first_project'.  Now, it is
already hosting a project. Now, I am working on a brand-new project, can I directly
push it to the same remote repo?

### 2.1) Reaction of the action:
I got the response from my local git as:
```
PS C:\Users\gulsh\OneDrive\Desktop\GitDemo1> git push origin main
To https://github.com/Gulshan028/first-project
! [rejected]        main -> main (fetch first)
error: failed to push some refs to 'https://github.com/Gulshan028/first-project'
hint: Updates were rejected because the remote contains work that you do not
hint: have locally. This is usually caused by another repository pushing to
hint: the same ref. If you want to integrate the remote changes, use
hint: 'git pull' before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
PS C:\Users\gulsh\OneDrive\Desktop\GitDemo1> 
```
It rejected the action straight away citing that the remote repository (`first-project` 
on GitHub) already contains commits that your local repository doesn't have. Git is 
preventing you from pushing because it would overwrite that existing work.

It provided the alternative solution as below-mentioned in point (2.2).

### 2.2) Alternative solution provided: Pull First, Then Decide
To see what's in the remote repository before deciding:

```bash
git pull origin main --allow-unrelated-histories
```
But, I am rejecting this idea for the below-mentioned reasons.
This will merge the old project with the new one (likely causing conflicts). You'd
then need to:
1. Resolve conflicts
2. Decide what to keep/delete
3. Push the merged result

This is messy and not recommended for completely different projects, hence rejecting this idea.

### 2.3) What My Options Are:

#### Option 1: Force Push (Replace Old Project Completely)
If you want to **completely replace** the old project with your new one:

```bash
git push -f origin main
```

⚠️ **Warning**: This will permanently delete all the old project's history and files 
from GitHub. Only do this if you're absolutely sure you don't need the old project 
anymore.
 
#### Option 2: Use a Different Branch
Keep the old project on `main` and put your new project on a separate branch:

```bash
git branch -m main new-project  # Rename your local branch
git push origin new-project      # Push to a new branch
```

#### Option 3: Create a New Repository (Recommended)
The cleanest solution is to create a new GitHub repository for your new project:

1. Go to GitHub and create a new repo (e.g., `second-project`)
2. Update your remote URL:
```bash
git remote remove origin
git remote add origin https://github.com/Gulshan028/second-project.git
git push origin main
```
### 2.4) My final actions:
- I went with option 1 to force push and it was successful. The previous project was of no use
  for me as I have a new project and both serve the same purpose. Thus, this was the better 
  approach for me.
- Also, I set upstream for the current branch using command `git push -u origin main`. 
  Thus, the upcoming commits of the `main` branch will be automatically pushed with the
  command `git push` simply.

### 2.5) My observations:
- On GitHub, when I visited my project. Then, on checking the commits, it showed what all
  files were added into a particular commit. Also, on opening each commit, it shows the entire 
  content of that file. The recent changes related to this commit into that file, i.e. delta, are  
  shown in green colour.
- The best part which I felt is that every information is visible there on the UI. 
  Like, the commit id, author_id, current branch, commit time and date, etc. 
- Sir sent his project to us as a GitHub link, and we were able to see his project at this
  link https://github.com/7sandeepsinha/LLDProject-Apr02-25. 
- Now, only accessing the code is not enough and hardly of any use. Thus, I need to download
  it onto my local machine for working on it.
- Now, downloading is of two kinds, viz. first time download and then further downloads
  are all about continuously fetching the updates. Let's see it in the next heading.
  
---

## III] Git `Clone` command: Demo and theory
1. On GitBash, we got out of our project folder named 'gitdemo2'. Then, we made another
   project directory named 'gitdemoPull'.
2. Now, in this folder, I used the command `git clone <remote_repo_link>`. Using this 
   command, I downloaded my previous project 'first-project's remote repo from GitHub.
   The actual command was `git clone https://github.com/Gulshan028/first-project`.
3. Now, this command has successfully downloaded the entire project. i.e. all the branches, 
   entire commit history, all the files, etc. 
4. So, a sub folder is automatically created inside `gitdemoPull` folder the remote repo 
   is saved as a subfolder with the same name as its remote repo. 
   In this case, the remote repo has the name `first-project`. Thus, the subfolder gets the 
   same name.
5. Thus, the thing to remember is that whenever a project is downloaded for the first 
   time from the remote repo then the command to be used is `git clone <remote_repo_link>`.
6. Now, this `first-project` is completely git enabled when downloaded.

### 3.1) My doubt:
once I `clone` a GitHub remote repo which is the project named `first-project` into a 
local folder named `gitdemoPull`. Then, in the local, I automatically get a subfolder
named `first-project` inside the folder `gitdemoPull`. Now, I haven't initialised 
i.e. git init in any of these folders. However, this project is automatically git 
managed? How, I am surprised?

### 3.2) Answer:- Why It's Automatically Git Managed:

When you **clone** a repository, you don't need to run `git init` because **cloning 
automatically sets up Git management**. Here's what happens:

#### (1) What `git clone` Does

When you run:
```bash
git clone <repository-url>
```

Git automatically:

1. **Creates the target directory** (`first-project` in your case)
2. **Initializes a Git repository** in it (equivalent to `git init`)
3. **Creates a `.git` folder** inside `first-project` with all the repository data
4. **Downloads all the commit history** from the remote
5. **Sets up a remote connection** called `origin` pointing to the cloned URL
6. **Checks out the default branch** (usually `main` or `master`)

#### (2) Why You Don't Need `git init`

- `git init` is only needed when you're **starting a repository from scratch**locally
- `git clone` is a **complete setup operation** - it does the initialization for you

#### (3) Verify It Yourself

Navigate into the cloned project and check:

```bash
cd gitdemoPull/first-project
ls -la  # You'll see a .git folder
git status  # This will work!
git remote -v  # Shows your remote connection
```

The `.git` folder (hidden by default) contains all Git's tracking information. That's 
why the project is "automatically" Git-managed - `clone` did all the setup work for you!

#### (4) Two Common Cloning Scenarios:

**Option 1: Clone into current directory (what you did)**
```bash
git clone https://github.com/username/first-project.git
# Creates: current-folder/first-project/
```

**Option 2: Clone with custom folder name**
```bash
git clone https://github.com/username/first-project.git my-custom-folder
# Creates: current-folder/my-custom-folder/
```
---

## IV] Git pull command: Demo and theory
To learn it, we have to act like we have two computers accessing the same GitHub remote
repo. For this, we will open the original folder 'gitdemo2' from intellij idea and 
the other folder 'gitdemoPull' wherein we have cloned the remote repo of 'gitdemo2' folder,
will be opened from Gitbash window. This way, we are replicating that there are two computers into action.

Thus, from the 'gitdemo2' folder via intellij idea, I added one line in 'myfile.txt' file 
about my MS plan and committed the change and pushed it to origin/main. Thus, now the 
remote repo 'first-project' is updated.

Now, my agenda is to download the updates from the remote repo. Since, this is the second
time download, I will use the`git pull` command as shown below:
`git pull origin main`.

### 4.1) What does this command do?
`git pull origin main` does **two things**:

✅ **1) `git fetch origin main` :- Fetches** the latest commits from the remote repository named **origin**, branch **main**
✅ **2) `git merge origin/main` :- Merges** those commits into your current local branch

So in simple words:

> **It updates your current branch with the latest changes from the `main` branch on the remote (`origin`).**

### 4.2) Important notes

⚠️ If your local branch has your own new commits, merge conflicts may happen.
You will have to resolve them manually.

---

In this way, this command has exactly updated the file with the latest change at the same
exact place.

---

## V] Difference between `git push`, `git pull` and `git clone`:
1. When we perform `git push origin main`; this command only the pushes the mentioned
   branch. It doesn't all the branches at local.
2. Now, imagine a remote repo has three branches, viz. `main`, `b1`, `b2`, etc.
When you do:

```bash
git clone <repo-url>
```

✅ Git **downloads the entire repository data**, including **all branches**, into `.git`
❌ But it **only creates one local branch** (usually `main` or `master`)

All other branches exist only as **remote-tracking branches**. They aren't present at local.

---

### 5.1) ✅ What you get after `git clone`

#### (1) Local branch created:

```
main   ← checked out
```

#### (2) Other branches are stored as *remote-tracking*:

```
origin/feature1
origin/feature2
origin/dev
...
```

You can see them with:

```bash
git branch -a
```

---

### 5.2) To work on another remote branch

You must create a local branch pointing to it:

```bash
git checkout -b feature1 origin/feature1
```

OR (newer syntax):

```bash
git switch -c feature1 origin/feature1
```

#### (1) ✅ What this command does

It **creates a new local branch named `feature1`**, starting from the remote-tracking
branch `origin/feature1`, and then **switches to it**.

So basically:

> **Make a new local branch called `feature1` → based on `origin/feature1` → and check it out.**

#### (2) 🔍 Breakdown of parts

| Part              | Meaning                              |
| ----------------- | ------------------------------------ |
| `git checkout`    | Switch branches                      |
| `-b feature1`     | Create a new local branch `feature1` |
| `origin/feature1` | Start from this remote branch        |

#### (3) ✅ Result

Before:

```
Local branches: main
Remote branches: origin/main, origin/feature1
```

After:

```
Local branches: main, feature1
Remote branches: origin/main, origin/feature1
Current branch: feature1
```

The new local `feature1` branch **tracks** changes from `origin/feature1`.

#### (4) When you use this?

When someone else has created a feature branch on the remote, and you want to work on it locally.

Example workflow:

1. You clone a repo
2. You list remote branches:

   ```
   git branch -a
   ```
3. You see:

   ```
   origin/feature1
   ```
4. You run:

   ```
   git checkout -b feature1 origin/feature1
   ```

   → Now you can work on it.

---
### 5.3) Difference between these commands:

| Command         | Shows               |
| --------------- | ------------------- |
| `git branch`    | Local branches      |
| `git branch -r` | Remote branches     |
| `git branch -a` | Both local + remote |

---

## VI] `git fetch -all` command:

The command `git fetch --all` is used to download the latest changes from **all remote
repositories and all their branches** without merging them into your local branches.

### 6.1) What `git fetch --all` does:

- **Downloads** the latest commits, branches, and tags from **all configured remote repositories**
- **Does NOT merge** or modify your working directory
- **Updates your remote-tracking branches (like `origin/main`, `origin/develop`, etc.)**
- **Safe** operation that doesn't change your local work

### 6.2) What happens after `git fetch --all`:

1. **You see what changed** without affecting your work:
   ```bash
   git log origin/main..main    # See what you have that remote doesn't
   git log main..origin/main    # See what remote has that you don't
   ```

2. **You can inspect** before merging:
   ```bash
   git checkout origin/main     # Checkout remote state to examine
   git diff main origin/main    # See differences
   ```

### 6.3) When to use `git fetch --all`:

- **Check for updates** without merging immediately
- **Multiple remotes** (origin, upstream, team members' repos)
- **Before creating branches** to ensure you're starting from the latest code
- **Review changes** before integrating them

### 6.4) Difference from `git pull`:

| Command | Fetches | Merges | Safe? | Use Case |
|---------|---------|---------|-------|----------|
| `git pull` | Yes | Yes | No* | Quick update & merge |
| `git fetch --all` | Yes | No | Yes | Review before merging |

*\*`git pull` can cause merge conflicts if you have local changes*

**Bottom line**: `git fetch --all` gives you the latest information from all remotes
so you can make informed decisions about when and how to integrate changes.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## VII] Fork
A **fork on GitHub** means:

> ✅ **You create your own copy of someone else’s repository (project) into your GitHub account.**

This allows you to:

* Modify the code freely
* Experiment without affecting the original project
* Submit changes back through a **pull request**

---

### 7.1) 🔍 Why fork?

Because you **cannot directly push changes** to someone else’s repo.
So you fork → make changes → then optionally ask the owner to merge your changes.

---

### 7.2) 🔄 Workflow

1. **Fork repo** → your copy on GitHub
2. **Clone your fork** → local machine
3. Make changes
4. Push changes to your fork
5. Make a **Pull Request (PR)** to the original repo

---

### 7.3) Example use case

You want to contribute to a popular open-source project.

You:

1. **Fork** it
2. Edit code
3. Create Pull Request
4. Owner reviews → merges your changes

---

### 7.4) Fork vs Clone

| Action    | Where                                |
| --------- | ------------------------------------ |
| **Fork**  | GitHub → copies repo to your account |
| **Clone** | Local machine → downloads a repo     |

Often we **fork first**, then **clone** our fork.

---

### 7.5) Simple definition

> **Fork = Your personal copy of someone else’s repo on GitHub.**

You control it; you can edit it without affecting the original.

---

### 7.6) Sir's task:
1. Fork sir's repo to our GitHub profile.
2. Clone this remote repo to our local machine
3. Add a new branch with our name
4. add a new file
5. push the changes to our own remote repo
6. Raise a pull request / merge request

## VIII] My Doubt:
Rather than fork, what if I directly connect to someone else's remote repo?

### 8.1) The answer:
Great question — this is the **real reason forks exist**.

#### (1) ✅ Why not just connect directly to someone else’s repo?

Technically, you **can** connect to another person’s remote repository:

```bash
git remote add origin <their-repo-url>
```

But…

## ❌ You won’t have permission to push

Most public GitHub repositories are **read-only** for outsiders.
You can **clone / fetch**, but you **cannot push** your changes.

So even if you connect directly, you’ll get an error when pushing:

```
! [rejected]  You don't have permission to push
```

Because it’s **not your repo**.

---

#### (2) ✅ Why fork is necessary

When you fork:
✅ You get **your own copy** under **your GitHub account**
✅ You **own it**, so you can push code freely
✅ You can develop independently
✅ You can still propose changes to the original repo (via Pull Request)

Without a fork, contributing to most projects would be impossible.

---

## IX] Pull Request:
Once I have done the work given by sir, then I am ready for a Pull request.
Pull request is simply asking to merge the changes done by me to the actual owner 
of the project, whose repo I had forked.

Then, sir or the owner of the project will review the changes and accordingly merge 
the changes or decline the changes. 

---

## X] Merge Conflict:
A merge conflict happens when two people change the exact same part of a file and 
then try to merge their work together.

Git, the version control system, gets confused because it doesn't know which person's
change to keep. It stops and asks a person to step in, look at both changes, and 
manually decide what the final file should contain.

There are several ways to resolve the merge conflict. However, the one which is used 
90% of the time is manual resolution. The developer has to manually choose the part to 
be kept. 

The second one is the below command: `git merge --abort` . This command just reverses the
merge which caused the conflict, and as a result, the branch comes back to its previous state. 
Then, you can take the decision and act accordingly.

NOTE: In real time development, once the feature has been merged successfully and there 
is no bug. Then, they wait for 7 days and post that the developers get the reminder to 
delete the feature branch to make the codebase cleaner.

## XI] Assignment:
1. **`git reset --mixed HEAD~1` does this:**

- **Removes the last commit** from your branch's history.
- **Keeps all the changes** from that commit as **unstaged files** in your working directory.

2. **To hard reset the topmost commit, use:**

```bash
git reset --hard HEAD~1
```

**What this does:**
- **Permanently deletes the most recent commit**
- **Moves your branch pointer** to the previous commit
- **Discards all changes** from that deleted commit

**Example:**
If your history is: `A - B - C (HEAD)`
After `git reset --hard HEAD~1`: `A - B (HEAD)`
Commit `C` and all its changes are gone forever.

**⚠️ Warning:** This is destructive! The commit and its changes are permanently lost
unless you have the commit hash to recover it.

To bring back this lost commit, use `git reflog` command to get the lost commit's 
commit_id and then use command `git reset --hard <lost_commit_id>`. This will brings 
back your lost commit due to hard reset.

3. **`git reflog` shows your local command history and reference movements.**

It displays **everywhere HEAD has pointed** - tracking all your actions like:
- Commits
- Checkouts
- Rebases
- Merges
- Resets

**In simple terms:** It's like a **safety net** that shows everything you've done in 
your local repository, even if you lost commits.

**Why it's useful:**
- **Recover lost commits** after accidental resets/deletes
- **Find commit IDs** you might have lost
- **See your workflow history**

**Example output meaning:**
```
47de2b1 HEAD@{1}: commit: Add new feature
446cfcc HEAD@{0}: reset: moving to HEAD~1
```
This shows you had commit `47de2b1`, then reset away from it.

**It only exists locally** - not shared with remote repositories.
