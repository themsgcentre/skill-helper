CREATE TABLE [dbo].[User] (
    [Username] [nvarchar](16) PRIMARY KEY,
    [Password] [nvarchar](max) NOT NULL,
    [ProfileImage] [nvarchar] (max) NULL,
    [Bio] [nvarchar](max) NULL,
);
