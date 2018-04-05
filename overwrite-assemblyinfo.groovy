import java.util.regex.*

def revision = "123"
def sha1 = "fdfd8sf"
def branchName = "feature/FLUX-1110-xdfsf"

def file = new File('C:/Users/Thomas/Travail/01-git-repositories/groovy-scripting/AssemblyInfo.cs')
def fileContent = file.text

def versionMatch = fileContent =~ /(\[assembly: Assembly(?:File)?Version\(\"\d*\.\d*\.\d*\.)(?:\d*)/

(0..<versionMatch.count).each{
    fileContent = fileContent.replace(versionMatch[it][0], versionMatch[it][1] + revision)
    file.newWriter().withWriter{
        w-> w << fileContent
    }
}

def fileVersionMatch = fileContent =~ /\[assembly: AssemblyFileVersion\(\"\d*\.\d*\.\d*\.\d*(.*)/
if (fileVersionMatch.find()){
    def updatedFileVersion = fileVersionMatch[0][0].replace(fileVersionMatch[0][1], " [$sha1] $branchName\")]")
    fileContent = fileContent.replace(fileVersionMatch[0][0], updatedFileVersion)

    file.newWriter().withWriter{
        w-> w << fileContent
    }
}
