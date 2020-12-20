package org.tnmk.replacing.all.checkdependencies;

import java.util.Objects;

public class Dependency {
  private String groupId;
  private String artifactId;
  private String version;

  private String link;
  private String fileName;
  private boolean accessible;

  public Dependency(String groupId, String artifactId, String version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Dependency that = (Dependency) o;
    return groupId.equals(that.groupId) &&
        artifactId.equals(that.artifactId) &&
        version.equals(that.version);
  }

  @Override public int hashCode()
  {
    return Objects.hash(groupId, artifactId, version);
  }

  @Override public String toString()
  {
    return "Dependency{" +
        "groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", version='" + version + '\'' +
        ", link='" + link + '\'' +
        ", fileName='" + fileName + '\'' +
        ", accessible=" + accessible +
        '}';
  }

  public String getGroupId()
  {
    return groupId;
  }

  public void setGroupId(String groupId)
  {
    this.groupId = groupId;
  }

  public String getArtifactId()
  {
    return artifactId;
  }

  public void setArtifactId(String artifactId)
  {
    this.artifactId = artifactId;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public boolean isAccessible()
  {
    return accessible;
  }

  public void setAccessible(boolean accessible)
  {
    this.accessible = accessible;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public String getLink()
  {
    return link;
  }

  public void setLink(String link)
  {
    this.link = link;
  }
}
