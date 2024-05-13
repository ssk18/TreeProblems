import java.util.*;

public class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(9);
        root.right.left = new TreeNode(6);
        System.out.println(search(root, 4));
    }


    // https://leetcode.com/problems/invert-binary-tree/description/
    static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;

        return root;
    }

    static TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        // Swap the left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // Recursively invert the left subtree
        invertTree1(root.left);

        // Recursively invert the right subtree
        invertTree1(root.right);

        return root;
    }

    public static TreeNode invertTreePostOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        // Recursively invert the left subtree
        invertTreePostOrder(root.left);

        // Recursively invert the right subtree
        invertTreePostOrder(root.right);

        // Swap the left and right children of the current node
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        return root;
    }

    // Level order traversal
    static void levelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> que = new ArrayDeque<>();
        que.add(root);
        while (!que.isEmpty()) {
            TreeNode current = que.peek();
            System.out.print(current.val + " ");
            if (current.left != null) que.add(current.left);
            if (current.right != null) que.add(current.right);
            que.poll();
        }
    }

    // https://leetcode.com/problems/binary-tree-level-order-traversal/
    static List<List<Integer>> levelOrderLeetcode(TreeNode root) {
        if (root == null) return Collections.emptyList();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                level.add(current.val);

                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }

            result.add(level);
        }
        return result;
    }

    // same problem where each node data is printed on anew line by level
    public static void levelOrderNewLine(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of elements at the current level

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll(); // Retrieve and remove the head of the queue
                System.out.print(current.val + " "); // Print current node's value

                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            System.out.println(); // Print a new line after each level
        }
    }

    // using a pair of root and its level
    public static void levelOrderPair(TreeNode root) {
        if (root == null) return;

        Queue<NodeLevelPair> queue = new ArrayDeque<>();
        queue.add(new NodeLevelPair(root, 0)); // Start from level 0

        int currentLevel = 0;
        while (!queue.isEmpty()) {
            NodeLevelPair pair = queue.poll();

            // Check if we need to advance to the next level
            if (pair.level > currentLevel) {
                System.out.println(); // Print a new line for a new level
                currentLevel = pair.level;
            }
            System.out.print(pair.node.val + " ");

            // Enqueue children with their respective levels
            if (pair.node.left != null) {
                queue.add(new NodeLevelPair(pair.node.left, pair.level + 1));
            }
            if (pair.node.right != null) {
                queue.add(new NodeLevelPair(pair.node.right, pair.level + 1));
            }
        }
    }

    static void levelOrderUsingHashMap(TreeNode root) {
        if (root == null) return;
        HashMap<Integer, List<Integer>> levelMap = new HashMap<>();
        Queue<NodeLevelPair> queue = new LinkedList<>();
        queue.offer(new NodeLevelPair(root, 0));

        while (!queue.isEmpty()) {
            NodeLevelPair currentPair = queue.poll();
            TreeNode currentNode = currentPair.node;
            int currentLevel = currentPair.level;

            levelMap.putIfAbsent(currentLevel, new ArrayList<>());
            levelMap.get(currentLevel).add(currentNode.val);

            if (currentNode.left != null) {
                queue.offer(new NodeLevelPair(currentNode.left, currentLevel + 1));
            }
            if (currentNode.right != null) {
                queue.offer(new NodeLevelPair(currentNode.right, currentLevel + 1));
            }
        }

        for (int level = 0; levelMap.containsKey(level); level++) {
            List<Integer> nodes = levelMap.get(level);
            for (int val : nodes) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // insert node in BST
    static TreeNode insert(TreeNode root, int k) {
        if (root == null) {
            return new TreeNode(k);
        }
        if (k < root.val) {
            root.left = insert(root.left, k);
        } else if (k > root.val) {
            root.right = insert(root.right, k);
        }
        return root;
    }

    // check for balanced binary search tree

    static int checkBBST(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = checkBBST(root.left);
        if (leftHeight == -1) return -1;

        int rightHeight = checkBBST(root.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    static boolean isBalanced(TreeNode root) {
        return checkBBST(root) != -1;
    }

    // search for a node in BST
    static boolean search(TreeNode root, int k) {
        if (root == null) return false;
        if (k > root.val) {
            return search(root.right, k);
        } else if (k < root.val) {
            return search(root.left, k);
        } else {
            return true;
        }
    }

    static int findMax(TreeNode root) {
        if(root == null) return Integer.MIN_VALUE;
        return Math.max(root.val, Math.max(findMax(root.left), findMax(root.right)));
    }

    // deletion
    static TreeNode delete(TreeNode root, int k) {
        if(root == null) return null;
        if (k > root.val) {
            root.right = delete(root.right, k);
        } else if (k < root.val) {
            root.left = delete(root.left, k);
        } else {
            if(root.left == null && root.right == null) {
                return null;
            }
            if(root.left == null) {
                return root.right;
            }
            if(root.right == null) {
                return root.left;
            }
            root.val = findMax(root.left);
            root.left = delete(root.left, root.val);
        }
        return root;
    }

    }