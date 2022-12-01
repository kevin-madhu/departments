package ru.rivc_pulkovo.domain.binary.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    private Node<Integer> orderedIntegerRootNode;

    private Node<Integer> unoderedIntegerRootNode;

    private List<Integer> expectedSerializationResult;

    @BeforeEach
    public void init() {
        this.orderedIntegerRootNode = new Node(10, true);

        //Create an unordered tree
        //            1
        //         /      \
        //        2      13
        //               /   \
        //              14  15
        this.unoderedIntegerRootNode = new Node(1);
        this.unoderedIntegerRootNode.addChildren(2, 13);
        this.unoderedIntegerRootNode.getRightChild().addChildren(14, 15);


        //1, 2, null, null, 13, 14, null, null, 15, null, null.
        this.expectedSerializationResult = new ArrayList<>();
        expectedSerializationResult.add(1);
        expectedSerializationResult.add(2);
        expectedSerializationResult.add(null);
        expectedSerializationResult.add(null);
        expectedSerializationResult.add(13);
        expectedSerializationResult.add(14);
        expectedSerializationResult.add(null);
        expectedSerializationResult.add(null);
        expectedSerializationResult.add(15);
        expectedSerializationResult.add(null);
        expectedSerializationResult.add(null);
    }

    @Test
    @DisplayName("Adding out of order children to an ordered node should return IllegalArgumentException.")
    void testAddChildren_whenTreeIsOrderedAndChildrenAreNotInOrder_throwsIllegalArgumentException() {
        //Given
        int leftChildValue = 4;
        int rightChildValue = 2;

        //When & Then
        assertThrows(IllegalArgumentException.class, () -> this.orderedIntegerRootNode.addChildren(leftChildValue, rightChildValue));
    }

    @Test
    @DisplayName("Adding ordered children to an ordered node should add the children successfully.")
    void testAddChildren_whenTreeIsOrderedAndChildrenAreInOrder_shouldAddChildrenNodes() {
        //Given
        int leftChildValue = 2;
        int rightChildValue = 3;

        //When
        this.orderedIntegerRootNode.addChildren(leftChildValue, rightChildValue);

        //Then
        assertEquals(leftChildValue, this.orderedIntegerRootNode.getLeftChild().getValue());
        assertEquals(rightChildValue, this.orderedIntegerRootNode.getRightChild().getValue());
    }

    @Test
    @DisplayName("Adding out of order children to an unordered node should add the children successfully.")
    void testAddChildren_whenTreeIsNotInOrderedAndChildrenAreNotInOrder_shouldAddChildrenNodes() {
        //Given
        int leftChildValue = 4;
        int rightChildValue = 2;

        //When
        this.unoderedIntegerRootNode.addChildren(leftChildValue, rightChildValue);

        //Then
        assertEquals(leftChildValue, this.unoderedIntegerRootNode.getLeftChild().getValue());
        assertEquals(rightChildValue, this.unoderedIntegerRootNode.getRightChild().getValue());
    }

    @Test
    @DisplayName("Serializing a tree should return properly serialized result.")
    void testSerialize_whenTreeIsSerialized_shouldReturnCorrectSerialization() {
        //Given

        //When
        var actualSerializationResult = this.unoderedIntegerRootNode.serialize();

        //Then
        assertEquals(expectedSerializationResult, actualSerializationResult);
    }

    @Test
    @DisplayName("Deserializing a tree should return properly deserialized tree.")
    void testDeserialize_whenTreeIsDeserialized_shouldReturnCorrectOriginalTree() {
        //Given

        //When
        var actualDeserializedResult = Node.deserialize(this.expectedSerializationResult);

        //Then
        assertEquals(this.unoderedIntegerRootNode, actualDeserializedResult);
    }

    //TODO - The test needs to be improved to test the whole tree
    // Preferably write a traverse method which accepts a Function within Node.
    // But then that needs to be tested.
    @Test
    @DisplayName("Deep copying a tree should return a completely different copy of the same tree.")
    void testDeepCopy_whenTreeIsDeepCopied_shouldReturnDifferentCopyOfSameTree() {
        //Given

        //When
        Node<Integer> unorderedIntegerRootNodeDeepCopy = this.unoderedIntegerRootNode.deepCopy();

        //Then
        assertNotEquals(this.unoderedIntegerRootNode.hashCode(), unorderedIntegerRootNodeDeepCopy.hashCode());
        assertEquals(this.unoderedIntegerRootNode, unorderedIntegerRootNodeDeepCopy);

        assertNotEquals(this.unoderedIntegerRootNode.getLeftChild().hashCode(), unorderedIntegerRootNodeDeepCopy.getLeftChild().hashCode());
        assertEquals(this.unoderedIntegerRootNode.getLeftChild(), unorderedIntegerRootNodeDeepCopy.getLeftChild());

        assertNotEquals(this.unoderedIntegerRootNode.getRightChild().hashCode(), unorderedIntegerRootNodeDeepCopy.getRightChild().hashCode());
        assertEquals(this.unoderedIntegerRootNode.getRightChild(), unorderedIntegerRootNodeDeepCopy.getRightChild());
    }

    @Test
    @DisplayName("Checking for equality of two completely different copy of the same tree should return true.")
    void testEquals_whenTreeIsCheckedForEqualityWithSameNodeStructure_shouldReturnTrue() {
        //Given

        //When
        Node<Integer> unorderedIntegerRootNodeDeepCopy = this.unoderedIntegerRootNode.deepCopy();

        //Then
        assertNotEquals(this.unoderedIntegerRootNode.hashCode(), unorderedIntegerRootNodeDeepCopy.hashCode());
        assertEquals(this.unoderedIntegerRootNode, unorderedIntegerRootNodeDeepCopy);
    }
}
