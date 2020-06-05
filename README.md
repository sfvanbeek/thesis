Lightweight testing framework for evaluating XACML polices.

## Requirements:
- Java 8
- Maven 3.x

## Installation:

In project root directory:

$ mvn clean package

## Run:

In project root directory:

$ run.sh <path-to-xacml-policyset> <path-to-request-file>

## Instruction:

Provide a path to a valid XACML policy and provide a path to a xml file containing requests.
Requests contain resource, subject, environment, and action attributes which can be referenced 
using AttributeDesignators in the XACML policy. The attributes are automatically assigned
to their respective XACML default categories, the subject being limited to: 
"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"

The output will be a decision result (PERMIT, DENY, NOT_APPLICABLE, INDETERMINATE) on per-request
basis, as such:

Alice accesses Alice's Patient File : PERMIT \
Alice accesses Bobs's Patient File : DENY

The name attribute of the Request element is mandatory.

Currently only STRING datatype is supported, matching http://www.w3.org/2001/XMLSchema#string

## Examle request XML
```$xml

<Requests name="Contains requests">
  <Request name="Alice accesses Alice's Patient File">
    <ActionAttribute>
      <DataType>STRING</DataType>
      <Id>ActionType</Id>
      <Values>
        <Value>Read</Value>
      </Values>
    </ActionAttribute>
    <SubjectAttribute>
      <DataType>STRING</DataType>
      <Id>PatientIdentifier</Id>
      <Values>
        <Value>Alice</Value>
      </Values>
    </SubjectAttribute>
    <ResourceAttribute>
      <DataType>STRING</DataType>
      <Id>FilePatientIdentifier</Id>
      <Values>
        <Value>Alice</Value>
      </Values>
    </ResourceAttribute>
    <EnvironmentAttribute>
      <DataType>STRING</DataType>
      <Id>Time</Id>
      <Values>
        <Value>Today</Value>
      </Values>
    </EnvironmentAttribute>
  </Request>
</Requests>


```