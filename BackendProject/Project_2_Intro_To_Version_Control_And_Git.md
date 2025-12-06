# Lecture | Intro to Version Control and Git, Commits, Merge, Rebase

## Contents:
1. Difference between Git and GitHub
2. Git's Difference Checking Mechanism
3. How each commit stores the snapshot of codebase at each commit?
4. Demo

## I]  Difference between Git and GitHub

### 1.1) The Core Difference: One-Liner

*   **Git** is a **tool**.
*   **GitHub** is a **service** for projects that use Git.

---

### 1.2) Git: The Version Control System (The Tool)

*   **What it is:** A distributed Version Control System (VCS) installed **locally on 
    your computer**.
*   **Analogy:** Git is like a powerful, unlimited "Save History" feature for your 
    project folder. It tracks every change you and your team make to the files.
*   **Key Function:** Manages the history of your source code and enables collaboration.
*   **Where it lives:** Primarily on your local machine.
*   **You use Git for:**
    *   Creating a repository (`git init`)
    *   Taking a snapshot (commit) of your changes (`git commit`)
    *   Creating and switching between different lines of development (`git branch`, `git checkout`)
    *   Merging changes from different branches (`git merge`)

> **Key Takeaway:** You can use Git perfectly fine without GitHub (e.g., for your 
  local private projects).

---

### 1.3) GitHub: The Hosting & Collaboration Platform (The Service)

*   **What it is:** A cloud-based **hosting service** for Git repositories.
*   **Analogy:** GitHub is like "Google Drive" or "Dropbox" for your Git repositories. 
    It provides a central, online place to store your code and collaborate with others.
*   **Key Function:** Provides a web-based GUI and social features to make working 
    with Git repositories easier and more collaborative.
*   **Where it lives:** In the cloud (on GitHub's servers).
*   **You use GitHub for:**
    *   Storing a backup of your repository in the cloud.
    *   Sharing your code with others.
    *   Collaborating on projects (e.g., using Pull Requests to propose changes).
    *   Reviewing other people's code.
    *   Reporting and tracking issues with software.
    *   Hosting simple websites directly from a repository (GitHub Pages).

> **Key Takeaway:** GitHub *enhances* Git by providing a centralized platform for 
  collaboration. It relies on Git to function.

---

### 1.4) Summary Table for Your Notes

| Feature | Git | GitHub |
| :--- | :--- | :--- |
| **Type** | Tool / Software | Service / Platform |
| **Installation** | Installed locally on your computer | Accessed via a web browser (github.com) |
| **Function** | Tracks changes and manages code history | Hosts repositories and enables collaboration |
| **Primary Use** | Version Control | Social Coding & Project Hosting |
| **Ownership** | Open-source, maintained by the Linux Foundation | A company (owned by Microsoft) |
| **Operation** | Command-line focused (mostly) | Web-based Graphical User Interface (GUI) |
| **Networking**| Can work fully offline | Requires an internet connection |
| **Alternatives**| (Git is the tool itself) | GitLab, Bitbucket, Azure Repos |

### 1.5) The Relationship in Practice

1.  You write code on your computer.
2.  You use **Git** locally to track your changes and create commits.
3.  You **push** your local Git repository to **GitHub** to back it up and share it.
4.  Your teammate **clones** the repository from **GitHub** to their machine (using Git).
5.  They make changes and **push** them back to **GitHub**.
6.  You **pull** their changes from **GitHub** to your machine (using Git) to get the 
    latest version.

**In short: Git is the heart of the operation, and GitHub is the meeting point that
gives it a home on the internet.**

---

## II] Git's Difference Checking Mechanism:
How Git Tracks Differences?
Git's mechanism for catching differences between versions is based on two key steps:

1.  File-Level Check (The Hash):

    - When you commit, Git assigns a unique identifier, or SHA-1 hash, to the exact 
      content of every file.
    - To compare version A to version B, Git first compares the hashes of the files.
    - If the hashes are identical, the files are considered exactly the same, and no 
      further string-by-string comparison is needed. This is extremely fast.

2.  Line-Level Check (The Diff Algorithm):

    - If the hashes are different, Git then runs a sophisticated diff algorithm (like 
      the Myers algorithm) on the file's contents.
    - This algorithm is what actually does the "string comparison" you mentioned, but 
      it's smarter than a simple character-by-character check. It's designed to find 
      the minimal, most logical set of changes (additions, deletions, and modifications) 
      to transform the old version into the new one, making the output human-readable.

In summary: Git uses hashing for fast content identification and only resorts to a 
sophisticated diff algorithm for the line-by-line comparison when a file has actually 
changed.

---

## III] How each commit stores the snapshot of codebase at each commit?

### 3.1) The Short Answer

Each Git commit **saves a complete snapshot of the entire codebase** at that moment 
in time. A commit in a git repository records a snapshot of all the (tracked) files 
in your directory. It's like a giant copy and paste, but even better!

### 3.2) The Detailed Explanation

When you run `git commit`, Git takes a snapshot of every single file in your repository
at that exact moment and stores a reference to that snapshot.

**However, and this is the crucial part,** Git does not store duplicate copies of 
unchanged files. It uses a brilliant, efficient method:

1.  **Git's Storage is Based on Snapshots, Not Diffs:** Unlike older version control 
    systems (like SVN) that store a base version and then a list of changes (diffs),
    Git stores the entire state of the project for each commit.

2.  **It Uses "Blobs" and Hashing:** Git doesn't store files by their name. It takes 
    the *content* of each file, runs it through an SHA-1 hash algorithm, and stores 
    this compressed "blob" in its database (inside the `.git` folder). The resulting 
    hash (e.g., `e0c03a3d...`) acts as a unique fingerprint for that specific content.

3.  **The Magic of Deduplication:** If a file hasn't changed between two commits, it 
    has the exact same content, and therefore the exact same hash. Instead of storing
    a second copy of the blob, the new commit just reuses the **pointer to the existing,
    identical blob**.

### 3.3) How You See "Changes"

When you use commands like `git log --patch` or `git diff`, Git is dynamically 
reconstructing the changes by comparing the snapshots.

*   `git diff COMMIT_A COMMIT_B` works by:
    1.  Loading the full snapshot of `COMMIT_A`.
    2.  Loading the full snapshot of `COMMIT_B`.
    3.  Comparing them line-by-line and showing you the differences.

So, while the stored data is a series of complete snapshots, the user-facing output 
is presented as a series of changes (**diffs**), which is what developers need to 
understand the history.

---

### 3.4) Summary

| What it Stores (Internally) | What it Shows You (As a User) |
| :--- | :--- |
| **A series of complete snapshots** of the entire project. | **A series of changes (diffs)** from one state to the next. |
| **Efficient** due to content-addressable storage and deduplication of unchanged files. | **Understandable** because you see what was added, removed, or modified. |

So, to directly answer your question: A commit **saves the entire codebase**, but it 
does so with the efficiency of a system that only tracks changes for storage purposes.

---

## IV] Demo
1. Firstly, I created an empty project from Intellij IDEA. It wasn't Git enabled. Thus, 
   I initialised the Git repository using the command, 'Git init'.
2. Then, I created two files into it named A.txt and B.txt respectively.
3. Now, once the project was initialized, then I first added the A.txt file using the 
   command 'git add A.txt'.
4. This `add` command is used to add the files to the staging area which are then ready
   for commiting. We can add as many files as we want by simply naming the files after the 
   `add` keyword in above command. 
5. We can also add all the files which are currently untracked using the command `git add .` .
6. Then the next step is to commit the changes using the command `git commit -m "commit_message"`
   By this command, all the files which were added to the staging area have been committed,
   it means they are now saved to their latest state i.e. including all the latest changes.
7. In Intellij IDEA, when the git repository is enabled, then the untracked files are shown in 
   red colour. Once they are added to the staging area, then their colour changes to green
   and as soon as they are committed, then finally their colour changes to white.
8. `git log` is the command which shows all the commits made in the project in the order of 
   latest timestamp first. So, each git commit consists of commit id, author name, timestamp
   and added file name.

---
## V] Git Branch
Yes, when you merge the `f1` branch into the `main` branch, the resulting merge commit
will incorporate the commit history from **both branches**.

Here's what happens:

### 5.1) The Merge Commit
- A new **merge commit** is created that has **two parents**
- First parent: the previous tip of `main` branch
- Second parent: the tip of `f1` branch
- This merge commit connects both histories

## Visual Example
```
Before merge:
main:  A -- B -- C
f1:     A -- D -- E

After merge:
main:  A -- B -- C -- F (merge commit)
                \       /
f1:     A -- D -- E
```

Where `F` is the merge commit with:
- Parent 1: commit `C` (from main)
- Parent 2: commit `E` (from f1)

### 5.2) What You'll See
- `git log` will show the complete history including commits from both branches
- The history appears as a unified timeline
- You can trace back through either parent line to see where changes came from

### 5.3) Key Points
- ✅ All commits from both branches remain in the history
- ✅ The merge commit references both parent commits
- ✅ You can use `git log --oneline --graph` to visualize the branching and merging
- ✅ The repository contains the complete, combined history

This is different from a **rebase**, which would rewrite history and create a linear
sequence without a merge commit.

---

### 5.4) Git Branch Demo:
1. The command `git branch -a` shows all the active branches present in the project.
   The current branch out of this list is shown in green colour and with the star mark.
   The remaining branches in the list are shown in the white colour. 
2. The current branch is the branch on which we are currently working and all the changes
   will be committed in this branch.
3. The command `git branch branchName` for e.g. `git branch f1` will create a new branch name 'f1'.
4. To move to a specific branch, we need to type the command `git checkout branchName`
   for e.g. `git checkout f1` will make the f1 branch as the current branch.
5. Now, we are in the 'f1' branch. So, if we see `git log` then we will the commit history of 
   the 'f1' branch.
6. For example: In my GitDemo1 project folder, I created C.txt file in the main branch
   then I won't be able to see that change in the f1 branch. 
   Similarly, if I am working in the f1 branch and add a few lines into A.txt file, then
   those changes won't be visible in the main branch unless merged.
7. We can use the command `git checkout -b branchName` for e.g. `git checkout -b feature3`
   this creates the new branch and also checkout to that branch, all at once.

### 5.5) Git Merge demo:
1. In the 'GitDemo' folder, there is 'main' branch. Then we create the f1 branch. 
2. Now, we did some work in the f1 branch and did multiple commits. After that, we want
   to merge this f1 branch into the main branch. 
3. For this to happen, we need to first check-out to main branch and then run command
   `git merge f1` and it will ask to insert the commit message. Insert the commit message 
   and then save and quit using the ":wq!" command after pressing esc key probably.
4. In this way, you have merged the f1 branch into the main branch.

### 5.6) Merge Conflict demo
1. We created another branch named feature2 and in the A.txt file changed the 1st line.
2. Similarly in the main branch, also we changed the first line of the same A.txt file
   and now we treid to merge this featurea2 branch into the main branch.
3. We got merge conflict with the below message.
```
   Auto-merging A.txt
   CONFLICT (content): Merge conflict in A.txt
   Automatic merge failed; fix conflicts and then commit the result.
   PS C:\Users\gulsh\OneDrive\Desktop\GitDemo1> 
```
It says that we tried to auto merge but because of the conflict in the A.txt file, we 
were unable to do so. Hence, the Automatic merge failed. It asks us to fix conflicts and 
then commit the result.
4. Then,  A.txt file is opened in front of us where data from both the files is shown.
   The data in the A.txt file from the current main branch is shown after "<<<<<<< HEAD"
   this symbol. Then, there is "=======" this symbol in the next line which acts as 
   separator for the data in the two files.
   Now, the data in the A.txt file from the feature2 branch is shown followed by 
   ">>>>>>> feature2" symbol.
5. Now, as a developer, I have full control of which version to keep or even change the content
   entirely. 
6. Thus I did the required changes and added the file to the staging area after resolving 
   the conflict using `git add .` then I committed the changes with a message.
7. With these steps, the two branches are merged. We can confirm it by checking `git log`
   there we can see that the latest commit is of merge commit, as shown below.
```
PS C:\Users\gulsh\OneDrive\Desktop\GitDemo1> git log
commit f54fc528e5341f87c1aafa5f1331e9bd8d405fa0 (HEAD -> main)
Merge: 3cc5ac8 39f579e
Author: Gulshan <gulshan02804@gmail.com>
Date:   Thu Oct 30 21:35:49 2025 +0530
```
in this message, in the 3rd line, we can see that the merge operation has been successful 
after resolving the merge conflict. This 3rd line shows the latest commit id of each of the 
parents of the merge commit. Thus, feature2 branch has been merged into the main branch.
8. Now, if you check A.txt file in the main branch then we will see the resolved content
   whereas if we checkout to feature2 branch and see the A.txt file then, we will continue to 
   see the old content of the A.txt file in the feature2 branch.

---

## VI] Git Rebase:
1. We have learned the theory of rebase and also written the same on the pdf file. Here, lets
   see the implementation of it on the below-mentioned website.
2. My goal is to git rebase 'f1' branch onto main branch. For this to happen, I need to be
   present on the 'f1' branch. So, first I checkout to f1 branch. Then I type the command
   `git rebase main` which will rebase the current branch 'f1' onto main branch.
3. So, on rebase all the commits of the 'f1' branch will be added onto the main branch.
4. But there is a catch here: we know each commit has multiple attributes and one of them
   is the commit-ids of previous commits. Also, each commit is immutable. i.e. we cannot change
   anything regarding a commit. So, if we rebase the feature 'f1' branch onto main, it means
   the parent commit of the first commit of this 'f1' branch will be changed. i.e. one of 
   the attributes of the commit will be changed. And this is not allowed because of immutability.
5. So, the solution for this is simple: On rebase, the copies of all the commits of the 
   feature branch are put onto the main branch. Thus, these new copy commits have the same contents
   as of commits of feature branch, it is just that its commit ids are different. i.e.
   They are altogether different commits with the same content.
6. You can try this on the below-mentioned website. Say feature branch 'f1' has commits
   C2, C3, C6 then its copies `C2'`, `C3'`, `C6'` will be joined onto the main branch post rebase.
7. Finally, we need to do the merge operation which is simply the fast-forward merge.
8. This thing justifies that Merge operation alone is equivalent to { rebase + merge } operation 
   done together in that sequence. This latter one has a cleaner history and has no merge commit.
9. The history is cleaner for the simpler reason that firstly we get all the commits from
   the feature branch i.e. `C2'`, `C3'`, `C6'` and all the commits of the main branch in the commit history.

---

## VII] HEAD pointer:
1. It is the pointer which points to the current active branch no matter whichever branch
   say main, f1, f2, f3, etc.

---

## VIII] Links:
1. learn git branching simulator: https://learngitbranching.js.org/
 - on this website, you can practice git commit, git branching, git merge, fast-forward
   merge and visualize the changes. You can also check the history of the commits.
 - You can see that when the git merge happens, then the main pointer points to the 
   merge-commit, whereas the feature branch f1 pointer continues to point at the same latest
   commit of the feature branch. 
2. Awesome reading material for this class: https://docs.google.com/document/d/1w-uU0vZOpT5Q1reABCt_raZedDG8CTEjNx8nNgI4dNc/edit?tab=t.0#heading=h.6gw7lgye8kq1

---

## IX] Doubt:
1. Ques:- I have a main git branch and a feature branch named 'f1', which is the 
   current active branch. Now, I do the commit in this f1 branch then will this commit
   save the changes in the main branch also?
   my doubt is simple during the new commit E on feature branch, does this commit have
   data about the main branch too? Since, each commit takes the snapshot of the entire
   project and I feel main branch is also the part of the project codebase.
   Ans:-
   Great question! This is a common point of confusion. Let me clarify:

**No, commit E does NOT contain data about the main branch.**

Here's the key understanding:

**What a commit actually snapshots:**
- A commit takes a snapshot of your **working directory** (the files you're currently working with)
- When you're on branch `f1`, your working directory contains the state of the `f1` branch
- The commit only knows about **its parent commit(s)**, not about other branches

**Branches are just pointers:**
- `main` and `f1` are just labels (pointers) that point to specific commits
- They're not separate codebases - they're different paths through the same commit history
- When you commit on `f1`, that commit points back to its parent (commit D), which eventually traces back to where `f1` branched off from `main` (commit C)

**Visual representation:**
```
main  →  A---B---C
              \
f1  →          D---E
```

Commit E contains:
- A snapshot of all files as they exist at point E
- A pointer to its parent commit D
- **No knowledge of where the `main` pointer is**

**The project codebase is not "main + f1"** - rather, each branch represents a *different version* of the entire project. When you're on `f1`, that **is** your entire project at that moment. The `main` branch is just another version existing in parallel in the git history.

---

# Sandeep Sir's class:

## I] About GitBash:
1. For Windows, use GitBash, which is the equivalent of Terminal on Mac. 

   ### 1.1) What is GitBash?
   Git Bash is an application for Microsoft Windows environments that provides an 
emulation layer for a Git command-line experience. It allows Windows users to interact
with Git, the distributed version control system, using a Bash (Bourne Again Shell)
command-line interface, which is the default shell for Linux and macOS.

   Essentially, Git Bash brings the powerful functionalities of Unix-based systems and 
the Git command-line to Windows, enabling developers to manage and control their 
projects more effectively. 

   It includes:
   - Git: The full set of command-line programs that constitute the Git version control system.
   - Bash: The popular shell, allowing users to execute Bash commands and other common Unix-like utilities (e.g., ls, cat, grep).
   - Mintty: A terminal emulator that provides a more robust and feature-rich command-line environment compared to the default Windows Command Prompt (cmd.exe).

   By using Git Bash, Windows users can perform all Git operations through a familiar 
   and widely used command-line interface, making it easier to follow tutorials and 
   documentation often written with a Unix-like environment in mind.

---

## II] `mkdir` command & what is a directory?
1. Now, in Gitbash, the command `mkdir <directory_name>` is used to make a new 
   directory, for ex: `mkdir gitdemo2`. This will create a project folder with the 
   name 'gitdemo2'. My entire project will be inside this folder.
2. "A directory in Git is simply a project folder that is being tracked by Git's 
   version control system." The directory is the visible project folder where you work,
   and Git tracks all changes within it (thanks to the hidden .git folder inside that 
   enables all the version control features).
3. Key Points:
   - **The `.git` Directory:** This is the heart of your repository. It's a hidden 
     folder that Git creates when you run `git init`. It contains all the version 
     control magic—the complete history and metadata of your project. **You should 
     never manually edit its contents.**
   - **The Working Directory:** This is everything outside the `.git` folder 
      (`src/`, `docs/`, `index.html`). This is what you see and work with.
   - **The Staging Area (Index):** This is a virtual area, not a visible directory. 
     It's a file within the `.git` folder that acts as a loading dock. You use 
     `git add` to place files from your Working Directory into the Staging Area to 
     prepare them for a commit.

---

## III] `cd` command:

### 3.1) What is `cd`?

`cd` stands for **"Change Directory"**. It's used to navigate between folders 
(directories) in your computer's file system through the command line or terminal.

### 3.2) Basic usage:

   - 
        ```bash
        cd [path_to_directory]
        ```

   - To change to a specific directory: Type `cd` followed by the directory's path.  
      - Example: `cd /home/user/Documents`

   - To change to your home directory: Use cd with no arguments.   
      - Example: `cd` or `cd ~` 

   - To move up one directory level ( to immediate parent directory): Use `..`.   
      - Example: `cd ..` 

   - To go back to the previous working directory: Use a single dash `-`.   
      - Example: `cd -` 

   - To navigate multiple levels: Chain the directories with a forward slash /.   
      - Example: `cd Documents/Reports` 

   - To move to a different drive (in Windows): Type the drive letter followed by a colon. 
      - Example: `J:` 
     
     - Go to the root directory
      ```bash
      cd /
      ```

### 3.3) Important Notes

- `cd` only works in command-line interfaces (Terminal, Command Prompt, PowerShell, 
   Git Bash, etc.)
- The directory path is case-sensitive on Linux and macOS, but usually not on Windows.
- You need to have permission to access the directory you're trying to enter.

So in summary: **`cd` is how you move between folders in the command line, which is 
essential for getting into your Git project directory before running Git commands.**

---

## IV] Few more Commands:

1. `ls` stands for "List". It is used to display the contents of a directory—showing 
    you what files and folders are inside.
2. `git init` stands for "Git Initialize". It's the command you use to create a new 
    Git repository from an existing directory, turning it into a version-controlled 
    project.
3. `git status` displays the current state of your working directory and staging area.
    It shows you which files have been modified, which are staged for commit, and 
    which aren't being tracked by Git.
4. `pwd` shows the current path of the directory.

---

## V] What is Vim?

- Vim is a powerful, keyboard-based text editor that runs in the terminal. It has a 
  learning curve but is very efficient once you learn it.
- The `vim testfile.txt` command in Git Bash opens the Vim text editor to create or 
  edit a file called `testfile.txt`.

### 5.1) Basic Usage

```bash
vim testfile.txt
```

This will:
- **Create** `testfile.txt` if it doesn't exist
- **Open** `testfile.txt` if it already exists
- Launch the Vim editor interface

### 5.2) Vim Modes (Crucial to Understand!)

Vim has different **modes**, which confuses beginners:

#### 1. **Normal Mode** (when you start)
- For navigation and commands
- **You can't type text directly**
- Press `Esc` to return to this mode

#### 2. **Insert Mode** (for typing text)
- Press `i` to enter Insert mode
- Now you can type and edit text normally
- Press `Esc` to return to Normal mode

#### 3. **Command Mode** (for saving/quitting)
- Press `:` in Normal mode to enter Command mode
- Type commands like `w` (write), `q` (quit)

### 5.3) Basic Vim Workflow

#### Step 1: Open the file
```bash
vim testfile.txt
```

#### Step 2: Enter Insert mode
Press `i` - you should see `-- INSERT --` at the bottom

#### Step 3: Type your content
Type whatever text you want:
```
Hello, this is my test file!
This is line two.
Line three for testing.
```

#### Step 4: Save and exit
1. Press `Esc` (return to Normal mode)
2. Type `:wq` and press `Enter`
    - `:w` = write (save)
    - `:q` = quit
    - `:wq` = save and quit

### 5.4) Essential Vim Commands

#### (1) Saving & Quitting
| Command | What it does |
|---------|--------------|
| `:w` | Save file |
| `:q` | Quit Vim |
| `:wq` | Save and quit |
| `:q!` | Quit without saving (force quit) |
| `:wq!` | Save and quit (force, if needed) |

#### (2) Editing & Navigation
| Command | What it does |
|---------|--------------|
| `i` | Enter Insert mode at cursor |
| `a` | Enter Insert mode after cursor |
| `o` | Insert new line below |
| `O` | Insert new line above |
| `x` | Delete character at cursor |
| `dd` | Delete entire line |
| `u` | Undo |

### 5.5) Common Scenarios

#### 1. You're stuck in Vim and can't type?
- Press `Esc` (probably in Normal mode)
- Type `:q` and `Enter` to quit

#### 2. Made changes but want to discard them?
```bash
Press Esc
Type :q!
Press Enter
```

#### 3. Quick create and exit:
```bash
vim testfile.txt
# Press i, type content, Press Esc, type :wq, press Enter
```

### 5.6) Alternative: Use a Simpler Editor

If Vim is too confusing, you can use:

```bash
# Nano (easier for beginners)
nano testfile.txt

# Or create file without editor
echo "Hello world" > testfile.txt
```

### 5.7) Complete Example

```bash
# Create and edit file
vim testfile.txt

# Inside Vim:
# 1. Press 'i'
# 2. Type: "This is my test file"
# 3. Press 'Esc'
# 4. Type ':wq'
# 5. Press 'Enter'

# Verify the file was created
cat testfile.txt
```

**In summary: `vim testfile.txt` opens the Vim editor to create/edit a text file. 
Remember the key modes: press `i` to type, `Esc` to command, and `:wq` to save/quit.**

Now, if I check with the `ls` command then I realise that there is one file with the name
'testfile.txt' in my project folder. Now, as I check the `git status` command, I find that 
there is an untracked file which is 'testfile.txt' . Let's understand how git manages the 
files in the project folder.

---

## VI] Git Workflow:
1. Git divides the project folder into three main areas:
   - **Working area/ Working directory:** Inside the git managed project folder, all those 
     files which are currently being worked on are inside this area. Now, if that file is
     a brand-new file then it is called as the 'untracked file' otherwise if it is an
     already tracked file which is later updated, then it is called as 'modified file'. 
   - **Staging area:** Now, in the project folder there can be 'n' number of files in the 
     working area. Out of those, not all the files are ready to be saved and committed. 
     Thus, those files are tested, and we are sure about those files to be committed then 
     we will `add` those files, and they become part of the staging area.
   - **Local Git Repository:** All those files which have been saved and committed are present
     under this area. After committing, files are stored securely in the local Git repository.
2. `git add testfile.txt testfile2.txt file3.txt` using this command we can add multiple 
   files into the staging area from the working area at once. Also, `git add .` this command
   helps to add all the files of the working area to the staging area at once.
3. `git commit -m "message"` this command is used to commit all the files from the staging 
   area. Write a short at max 5-6 words message that clearly defines your commit.
4. To remove any files from the staging area back to the working area, we need to type 
   command which is specified as `git rm --cached <fileName>`.

---

## VII] My doubt: 
you want to say that if the file has not been added into the staging area, then it is
untracked file and will continue to show across all the branches and cannot be deleted
with the command `git rm - <filename>`

### **7.1) Answer:- Untracked Files Behavior:**

#### (1) **What are untracked files?**
- Files that exist in your working directory but **have never been added to Git** (`git add`)
- Git doesn't track these files in its version control

#### (2) **Key Points:**
1. **`git rm` only works on tracked files** (files that have been committed or added to staging area)
2. **Untracked files are local to your workspace** and appear across all branches in that local repository
3. **Untracked files are not part of Git's version control**, so they don't belong to any specific branch

#### (3) **Example:**
```bash
# Create a new file (untracked)
echo "content" > testfile3.txt

# This will FAIL because file is untracked
git rm testfile3.txt
# fatal: pathspec 'testfile3.txt' did not match any files

# This will WORK - delete it manually
rm testfile3.txt
```

#### (4) **How to handle untracked files:**
```bash
# Method 1: Delete manually
rm filename.txt

# Method 2: Use git clean (for multiple untracked files)
git clean -n  # dry run - see what will be deleted
git clean -f  # actually delete untracked files

# Method 3: Add to .gitignore to hide them
echo "filename.txt" >> .gitignore
```

#### (5) **Visualizing the difference:**
- **Tracked files**: Can use `git rm`, belong to specific commits/branches
- **Untracked files**: Must use regular `rm`/`del`, exist locally across branches

So your understanding is perfect! If `testfile3.txt` is untracked, you need to use 
regular file deletion commands, not `git rm`.

---

## VIII] Git Branch:
- The branches in Git allows developers to develop features for the application, test it,
  debug it, and do all other things to make it production ready. 
- Your work in a branch doesn't affect the main branch or any other branch. It is independent.
- Once your feature is ready, then you can merge your feature branch into the parent branch. 
- NOTE: as you move across branches, you can see files in them directly from your UI i.e.
  from your 'File Explorer aka file manager'.
- For merging, you need to come to parent branch first and then type the command `git merge <branchName>`.

---

## IX] My doubt:
In Git , I made a branch named `f1` from `main` branch. Now, after some time, 
I made a branch from `main` itself and named it `f2`. Later, I created another branch 
from `f1` branch and named it `f3`. Now, question 1: can I merge `f1` to `f2`?
question 2: can i merge `f3` to `main` directly?
also, for a given branch, how can I know its parent branch?
answer them one by one.

### Answer:
I'll answer each question one by one:

#### 9.1) Question 1: Can I merge f1 to f2?

**Yes, absolutely.** You can merge any branch into any other branch in Git. The 
branches don't need to have a parent-child relationship.

To merge f1 into f2:
```bash
git checkout f2
git merge f1
```

This will merge all commits from f1 into f2. Git doesn't enforce any hierarchical 
restrictions on merging.

#### 9.2) Question 2: Can I merge f3 to main directly?

**Yes, you can.** Again, Git allows you to merge any branch into any other branch, 
regardless of their creation history.

To merge f3 into main:
```bash
git checkout main
git merge f3
```

When you merge f3 into main, you'll get all the commits from f3, which includes:
- The commits from f1 (since f3 was branched from f1)
- Plus any new commits made on f3

#### 9.3) How to know a branch's parent branch?

**Git doesn't actually track "parent" branches.** When you create a branch, Git only remembers:
- The commit point where the branch was created
- The commits on that branch

However, you can find the "closest common ancestor" or see where a branch diverged from another using:

```bash
# Find the merge-base (common ancestor) between two branches
git merge-base f3 main

# See which commit a branch was created from
git reflog show f3

# Visualize branch history
git log --graph --oneline --all
```

The `git log --graph --oneline --all` command is particularly useful as it shows a 
visual tree of all branches and their relationships, helping you understand where each
branch diverged from others.

---

## X] My Doubt:
I wrote a line in `file.txt` in `main` branch and committed it. Then, I created and 
switched to `feature` branch from `main` branch and added 2nd line in the same file 
and committed it in feature branch.
So, as soon as I made the `feature` branch, there was just one copy of `file.txt` 
but as soon as I committed the change in the `feature` branch, now there are 2 copies.
One copy is in `main` branches' latest commit and another copy is in `feature` branch.
Am I correct in this understanding?

### Answer to:📝 Git Branching and File Storage Summary

### 10.1) 🌳 Branch Creation: Pointers, Not Copies

* When you create a new branch (e.g., `feature` from `main`), Git **does not** 
  duplicate the entire codebase.
* It is incredibly fast because it only creates a **lightweight pointer** 
  (a reference) that points to the **exact same final commit** as the parent branch.
* Both branches initially share all file content and history.

### 10.2) 💾 The Snapshot Principle: Storing Full Content

Git stores file content using a **snapshot model**, which means:

* When you make a new commit, Git creates a **new blob object** for every file that **changed**.
* This new blob object stores the **complete content of the file** as it exists at that moment. 
  It does **not** just store the changes (deltas or differences) between the old and 
  new version.

### 10.3) 🔪 File Separation Upon Commit

Let's look at your example with `file.txt`:

| Step | Branch State | Git's Storage Action | Resulting File Content (Blob) |
| :--- | :--- | :--- | :--- |
| **Commit 1 (on `main`)** | `main` and `feature` point to Commit 1. | Git saves the full file content as **Blob A**. | **Blob A** stores: "Line 1" |
| **Commit 2 (on `feature`)** | `feature` moves to Commit 2; `main` stays at Commit 1. | Git sees the file content is new and saves the **full new file content** as **Blob B**. | **Blob B** stores: "Line 1\nLine 2" |

* **Conclusion:** "There is only one `file.txt` in your current folder / working directory at any time. 
  In Git's object database, you now have two blob objects (Blob A and Blob B), each storing complete
  file content. **Commit 1 references Blob A, and Commit 2 references Blob B.** When you
  switch branches, Git checks out the appropriate commit and reconstructs `file.txt`
  from the corresponding blob."
* The key distinction: **Blobs belong to commits, not branches.** Branches just point
  to commits, and commits reference blobs.

This mechanism ensures Git is fast, reliable, and can jump instantly to any version 
without having to rebuild the file from a long sequence of changes.

---

## Links:
1. Git commands: https://ubuntu.com/tutorials/command-line-for-beginners#4-creating-folders-and-files

