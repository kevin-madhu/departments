package ru.rivc_pulkovo.domain.binary.tree;

import java.util.ArrayList;
import java.util.List;

class Node<T extends Comparable<T>> {
    private T value;

    private final boolean isOrdered;

    private Node<T> leftChild;

    private Node<T> rightChild;

    Node(T value, boolean isOrdered) {
        this.value = value;
        this.isOrdered = isOrdered;

        this.leftChild = null;
        this.rightChild = null;
    }

    Node(T value) {
        this(value, false);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void addChildren(T leftValue, T rightValue) {
        if(this.isOrdered && leftValue.compareTo(rightValue) > 0) {
            throw new IllegalArgumentException("Children have to be in order.");
        }

        this.leftChild = new Node<>(leftValue, this.isOrdered);
        this.rightChild = new Node<>(rightValue, this.isOrdered);
    }

    public List<T> serialize() {
        List<T> serializedTree = new ArrayList<>();
        serializedTree.add(this.value);

        if(this.leftChild != null) {
            serializedTree.addAll(this.leftChild.serialize());
        } else {
            serializedTree.add(null);
        }

        if(this.rightChild != null) {
            serializedTree.addAll(this.rightChild.serialize());
        } else {
            serializedTree.add(null);
        }

        return serializedTree;
    }

    public static <C extends Comparable<C>> Node<C> deserialize(List<C> nodeValues) {
        C nodeValue = nodeValues.remove(0);

        if(nodeValue == null) {
            return null;
        }

        Node<C> rootNode = new Node<>(nodeValue);
        rootNode.setLeftChild(deserialize(nodeValues));
        rootNode.setRightChild(deserialize(nodeValues));

        return rootNode;
    }

    Node<T> deepCopy() {
        Node<T> newRootNode = new Node<>(this.value);

        if(this.leftChild != null) {
            newRootNode.leftChild = this.leftChild.deepCopy();
        }

        if(this.rightChild != null) {
            newRootNode.rightChild = this.rightChild.deepCopy();
        }

        return newRootNode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Node)) {
            return false;
        }

        return serialize().equals(((Node) obj).serialize());
    }
}
